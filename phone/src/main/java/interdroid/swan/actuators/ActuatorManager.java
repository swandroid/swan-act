package interdroid.swan.actuators;

/**
 * Created by rm on 15/10/2016.
 */

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import interdroid.swan.R;
import interdroid.swan.actuators.actuators.Actuator;
import interdroid.swan.actuators.actuators.PluginActuator;
import interdroid.swan.actuators.adapters.ExpressionAdapter;
import interdroid.swan.actuators.communication.InitiateVirtualSensor;
import interdroid.swan.actuators.communication.RemoteDeviceActuatorComm;
import interdroid.swan.actuators.database.ActionManager;
import interdroid.swan.actuators.database.RuleManager;
import interdroid.swan.actuators.database.SwanExpManager;
import interdroid.swan.actuators.entities.Action;
import interdroid.swan.actuators.entities.SwanActRule;
import interdroid.swan.actuators.entities.SwanExp;
import interdroid.swan.actuators.idmanagement.GetId;
import interdroid.swan.actuators.jobs.JobManager;
import interdroid.swan.actuators.jobs.JobRequest;
import interdroid.swan.actuators.jobs.util.support.PersistableBundleCompat;
import interdroid.swan.actuators.performance.Monitor;
import interdroid.swan.actuators.scheduler.RedBlackBST;
import interdroid.swan.actuators.swan.SwanSensor;
import interdroid.swan.actuators.swanactuatorssong.ActuatorSong;
import interdroid.swan.actuators.utils.UUIDInstallation;
import interdroid.swancore.swanmain.ExpressionManager;
import interdroid.swancore.swanmain.SensorInfo;

import org.droidparts.activity.Activity;
import org.droidparts.annotation.bus.ReceiveEvents;
import org.droidparts.annotation.inject.InjectDependency;
import org.droidparts.annotation.inject.InjectView;

import static android.content.ContentValues.TAG;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class ActuatorManager extends Activity {

    @InjectView(id = R.id.expressions)
    ListView mExpressions;

    @InjectDependency
    private ActionManager mActionManager;

    @InjectDependency
    private RuleManager mRuleManager;

    @InjectDependency
    public static SwanExpManager mSwanExpManager;

    private ExpressionAdapter mExpressionView;

    RequestQueue mRequestQueue;

    private JobManager mJobManager;

    public static int check=0;

    public static String selfId;

    public static HashMap<String,String> parameters = new HashMap<String,String>();

    public static HashMap<String,String> runningExpressions=new HashMap<String,String>();

    public static HashMap<String,Integer> runningExpressionsCount=new HashMap<String,Integer>();

    public static HashMap<String,SwanSensor> expressions=new HashMap<String,SwanSensor>();

    public static Queue<String> evaluationQueue=new LinkedList<String>();

    Queue<String> startQueue=new LinkedList<String>();

    HashMap<String,String> runningQueue=new HashMap<String,String>();

    public static RedBlackBST<Long, String> evaluationTree = new RedBlackBST<Long, String>();

    String currentExpression="base";

    String expressionId;

    public static int currentId=0;

    GetId gi=new GetId();

    public static int checkRestore=0;

    @Override
    public void onPreInject() {
        super.onPreInject();
        setContentView(R.layout.activity_rules);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check if existing expression ids are executing or not ?
        /**
         * If the triggers are executing then good if not restart them
         * check if the expression is still registered with SWAN
         * UNREGISTER AND START WITH SAME EXPRESSION ID (TRIGGER)
         *
         */
        final ArrayList<SwanExp> rules = mSwanExpManager.getAll();
        if(evaluationQueue.isEmpty()){
            if(rules.isEmpty()==TRUE){
                checkRestore=1;
            }
            else{
                for(int i=0;i<rules.size();i++){
                    /*
                    First check the highest number for restored job
                     */
                    String[] splitTrigger=rules.get(i).getTrigger().split(",");
                    currentId=Integer.parseInt(splitTrigger[1]);
                    try {
                        ExpressionManager.unregisterExpression(getApplicationContext(), rules.get(i).getTrigger());
                    }
                    catch (Exception ex){
                    }
                    runningQueue.put(rules.get(i).getExpId(),rules.get(i).getTrigger());
                    startQueue.add(rules.get(i).getExpId()+"*"+rules.get(i).getTrigger());
                    rules.remove(i);
                }
                checkRestore=1;
            }
        }

        mExpressionView = new ExpressionAdapter(this, (new SwanExpManager(this)).select());
        mExpressions.addHeaderView(new View(this));
        mExpressions.addFooterView(new View(this));
        mExpressions.setAdapter(mExpressionView);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("actuator-data-received"));

        //For getting remotedata between actuators on different devices
        LocalBroadcastManager.getInstance(this).registerReceiver(mRemoteMessageReceiver,
                new IntentFilter("remote-data-received"));

        //For getting registration related details for an expression
        LocalBroadcastManager.getInstance(this).registerReceiver(mExpressionMessageReceiver,
                new IntentFilter("expression-data-received"));

        LocalBroadcastManager.getInstance(this).registerReceiver(mMonitorReceiver,
                new IntentFilter("monitor-data-received"));

        /**
         *Instatntiate an instance of job manager
         */
        JobManager.create(getApplicationContext()).addJobCreator(new DemoJobCreator());
        mJobManager = JobManager.instance();

        /**
         * Register device information
         */
        UUIDInstallation uuidInstallation=new UUIDInstallation();
        String uniqueID = UUIDInstallation.id(this);
        selfId=uniqueID;

        String googleAccount="";
        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Account[] accounts = accountManager.getAccountsByType(null);
        for (Account account : accounts) {
            if(account.type.contentEquals("com.google")){
                googleAccount=account.name;
            }
                Log.d(TAG, "account: " + account.name + " : " + account.type);
            }

        postDeviceInfo(uniqueID,googleAccount);

        ///MONITOR RESOURCES AND SCHEDULE
        new Thread(new Runnable() {
           @Override
           public void run() {
               Monitor monitor = new Monitor();
               monitor.mointorUsage();

           }
       }).start();

        String expression0="self@light:lux{ANY,100ms}<10.0THENself@expression:start?exp='self@light:lux{ANY,1000ms}<10.0THENself@vibrator:vibrate'";
        //add(expression0);

        String expression6="self@light:lux{ANY,100ms}<10.0THENself@feedback:before?expression='self@light:lux{ANY,100ms}<10.0'&window='MAX,1h'";
        //add(expression6);

        String expression1="self@light:lux{ANY,100ms}THENself@ThingsSpeak:put?api_key='QO09390QBHFXO82N'&field='field1'";
        //add(expression1);

        String expression7="self@light:lux{ANY,100ms}THENself@ThingsSpeak:put?api_key='QO09390QBHFXO82N'&field='field2'";
        //add(expression7);

        String expression2="self@light:lux{ANY,100ms}<10.0THENlsra{04397aab-3dc5-4eec-9450-5e557896a4f5,04397aab-3dc5-4eec-9450-5e557896a4f5}@vibrator:vibrate";
        //add(expression2);

        String expression5="self@light:lux{ANY,100ms}<10.0THENrsla{04397aab-3dc5-4eec-9450-5e557896a4f5,04397aab-3dc5-4eec-9450-5e557896a4f5}@vibrator:vibrate";
        //add(expression5);

        String expression3="self@light:lux{ANY,100ms}<10.0THENremote{050a34fc-c9ea-4cd8-82a4-a606e501df11}@vibrator:vibrate";
        //add(expression3);

        String expression4="self@light:lux{ANY,100ms}<20.0THENself@ThingsSpeak:put?api_key='QO09390QBHFXO82N'&field='field1'^^self@vibrator:vibrate'";
        //add(expression4);



        List<SensorInfo> sensorInfoList=ExpressionManager.getSensors(this);
        SendSensorToBuilder sendSensorToBuilder=new SendSensorToBuilder();
        for(int i=0;i<sensorInfoList.size();i++){
            //sendSensorToBuilder.postMessageToDevice(this,sensorInfoList.get(i).getEntity(),sensorInfoList.get(i).getValuePaths());
        }
        ArrayList<Actuator> actuatorsToWeb = Actuator.getActuators();

        Map<String,ArrayList>actuatorsList=new HashMap<String,ArrayList>();

        for (int i=0; i< actuatorsToWeb.size(); i++) {
            //sendSensorToBuilder.postMessageToDevice(this,actuatorsToWeb.get(i).getActuator(),actuatorsToWeb.get(i).getOptions());
            //actuatorDescriptions[i] = actuators.get(i).describeActuator();
            //actuatorOptions.add()
            ArrayList<String> actuatorOptions=new ArrayList<String>();

            String [] optionsList=actuatorsToWeb.get(i).getOptions();
            for (int k=0;k<optionsList.length;k++){
                actuatorOptions.add(optionsList[k]);
            }
            actuatorsList.put(actuatorsToWeb.get(i).getActuator(),actuatorOptions);
        }

        for (Map.Entry<String, ArrayList> entry : actuatorsList.entrySet())
        {
            String key = entry.getKey();
            ArrayList value = entry.getValue();
            //sendSensorToBuilder.postMessageToDevice(this,key,value);
            //use key and value
        }

        ExpressionReceiver mReceiver = new ExpressionReceiver();
        registerReceiver(mReceiver, new IntentFilter("ExpressionRequests"));
    }

    private void add(String expression){
        //while(checkRestore==0){}

        String [] checkMultipleExpressions=expression.split("THEN",2);
        String sensorExpression=checkMultipleExpressions[0];
        String [] actuatorExpressions=checkMultipleExpressions[1].split("\\^\\^");
        String trigger=gi.getId(this);

        if(actuatorExpressions!=null){
            for(String act:actuatorExpressions){
                String expressionToRegister=sensorExpression+"THEN"+act;
                //Comma seperated trigger and complete expression
                runningQueue.put(expressionToRegister,trigger);
                startQueue.add(expressionToRegister+"*"+trigger);
            }
        }
        /*String trigger=gi.getId(this);
        //Comma seperated trigger and complete expression
        runningQueue.put(expression,trigger);
        startQueue.add(expression+"*"+trigger);*/
    }

    /**
     *
     * @param expression expression from remote
     * @param userId user assigned id
     */
    private void addExternal(String expression,String userId){
        //while(checkRestore==0){}
        String [] checkMultipleExpressions=expression.split("THEN",2);
        String sensorExpression=checkMultipleExpressions[0];
        String [] actuatorExpressions=checkMultipleExpressions[1].split("\\^\\^");
        String trigger=gi.getId(this);

        runningQueue.put(userId,expression+"*"+trigger);

        if(actuatorExpressions!=null){
            for(String act:actuatorExpressions){
                String expressionToRegister=sensorExpression+"THEN"+act;
                //Comma seperated trigger and complete expression
                startQueue.add(expressionToRegister+"*"+trigger);
            }
        }
        /*runningQueue.put(userId,expression+"*"+trigger);
        runningQueue.get(userId);
        startQueue.add(expression+"*"+trigger);*/
    }

    private void init(String expression,String trigger){
        evaluationQueue.add(expression+"*"+trigger);
        String checkLocation=checkLocation(expression);
        registerExpression(expression,trigger,checkLocation);
    }

    private String checkLocation(String expression){

        String actuationType="";

        ActuatorSong as=new ActuatorSong();
        as.expressionParse(expression);
        String actuatorLocation=as.deviceId;
        String[] requestTypeRsla=actuatorLocation.split("rsla",2);
        String[] requestTypeLsra=actuatorLocation.split("lsra",2);
        String[] requestTypeRemote=actuatorLocation.split("remote",2);

        if(actuatorLocation.contentEquals("self")==TRUE) {
            actuationType="local";
        }
        else if(requestTypeRemote.length>1) {
            actuationType="fullyremote";
        }
        else if(requestTypeRsla.length>1) {
            actuationType="remoteswanlocalact";
        }
        else if(requestTypeLsra.length>1) {
            actuationType="localswanremoteact";
        }
        return actuationType;
    }

    /**
     * This broadcast receiver receives requests for plugin
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int id=intent.getIntExtra("id",0);
            String actuator = intent.getStringExtra("actuator");
            String options=intent.getStringExtra("options");
            String describeActuator=intent.getStringExtra("describe");
            String packageName=intent.getStringExtra("packagename");
            String serviceName=intent.getStringExtra("servicename");
            String deviceId=intent.getStringExtra("deviceid");
            PluginActuator rm=new PluginActuator(deviceId,id,actuator,options,describeActuator,packageName,serviceName);
            Actuator.mActuators.put(id,rm);
            Actuator.mActuators.get(id).setId(id);
        }
    };
    /**
     * This broadcast receiver gets data on remote actuation
     * called by the firebase messaging service
     *
     */
    //remote-data-received
    private BroadcastReceiver mRemoteMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String actuationtype = intent.getStringExtra("actuationtype");
            String completeexpression;
            String data;
            switch (actuationtype){
                case "remoteswanlocalact" :
                    completeexpression=intent.getStringExtra("completeexpression");
                    data=intent.getStringExtra("data");
                    loadvirtualsensor(completeexpression,data,actuationtype,"null","null");
                    break;

                case "fullyremote" :
                    completeexpression=intent.getStringExtra("completeexpression");
                    data=intent.getStringExtra("data");
                    add(completeexpression);
                    break;

                case "localswanremoteact" :
                    completeexpression=intent.getStringExtra("completeexpression");
                    data=intent.getStringExtra("data");
                    String [] expressionFrom=completeexpression.split("\\*");
                    String [] expressionLoc= expressionFrom[0].split(",");
                    String expressionLocation=expressionLoc[0];
                    completeexpression=expressionLocation+expressionFrom[1];
                    Log.d("BOTCHWAY",data);
                    try {
                        ActuatorManager.expressions.get(completeexpression).remoteStream(data);
                    }
                    catch (NullPointerException ne){
                    }
                    break;

                case "createvirtualsensor" :
                    completeexpression=intent.getStringExtra("completeexpression");
                    data=intent.getStringExtra("data");
                    String senderid=intent.getStringExtra("senderid");
                    String receiverid=intent.getStringExtra("receiverid");
                    createvirtualsensor(completeexpression,data,actuationtype,senderid,receiverid);
                    break;

                case "register" :
                    completeexpression=intent.getStringExtra("completeexpression");
                    data=intent.getStringExtra("data");
                    //String senderid=intent.getStringExtra("senderid");
                    //String receiverid=intent.getStringExtra("receiverid");
                    if(runningQueue.get(data)==null) {
                        addExternal(completeexpression,data);
                    }
                    else{
                        Toast.makeText(context, "ID ALREADY EXISTS CHOOSE ANOTHER", Toast.LENGTH_LONG).show();
                    }
                    break;

                case "unregister" :
                    data=intent.getStringExtra("data");
                    try {
                        String userExpression = runningQueue.get(data);
                        String[] userExpressionData = userExpression.split("\\*");
                        String[] userExpressionToUnregister=userExpressionData[0].split("THEN",2);
                        SwanSensor swanSensorToStop = ActuatorManager.expressions.get(userExpressionToUnregister[0]);
                        if(swanSensorToStop==null){
                        }
                        else{
                            swanSensorToStop.unregisterActuator(userExpressionData[1]);
                            runningQueue.remove(data);
                        }
                    }
                    catch (NullPointerException ne){
                    }
                    break;
            }

        }
    };
    /**
     * When an external entity makes request for registering an expression
     */
    private BroadcastReceiver mExpressionMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String expression = intent.getStringExtra("expression");
            add(expression);
        }
    };

    /**
     * Get Resource data from the monitor and post it for analysis and scheduling
     */
    private BroadcastReceiver mMonitorReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Integer level = intent.getIntExtra("level",0);
            Long duration = intent.getLongExtra("duration",0);
            //Posts to remote server
            //postBatteryStats(level.toString(),duration.toString());
            evaluationTree.put(duration,currentExpression);
            ///Add Expression to execute
            if(startQueue.isEmpty()==FALSE){
                currentExpression=startQueue.poll();
                //get trigger and expression to act
                String[] expressionAndTrig=currentExpression.split("\\*");
                init(expressionAndTrig[0],expressionAndTrig[1]);
            }
            else{
                for(int i=0;i<evaluationQueue.size();i++) {
                    String[] expressionAndTrigToPause=evaluationQueue.poll().split("\\*");
                    String[] expression=expressionAndTrigToPause[0].split("THEN",2);
                    SwanSensor swanSensorToStop=ActuatorManager.expressions.get(expression[0]);
                }
            }
        }
    };
    /**
     *
     * @param expression The expression SWAN+SWAN-Act
     * @param trigger The unique expression ID goes through here
     * @param actuatorType If remote, localswanremoteact, remoteswanlocalact
     */
    public void registerExpression(String expression,String trigger, String actuatorType){
        //Parse the expression for details where expression would be run
        //An expression registered on other device actuates on this device
        String actuationRequest=actuatorType;
        String [] one;
        String [] three;
        String actLocation;
        String [] locationParams;
        String [] loca;
        Pattern p;
        Matcher m;
        RemoteDeviceActuatorComm remoteDev = new RemoteDeviceActuatorComm();
        String locationParamsCommaSeperated="";
        String expressionModified;

        switch (actuationRequest) {
            case "remoteswanlocalact" :
                one= expression.split("THEN");
                three=one[1].split("\\@");
                actLocation=three[0];
                loca=actLocation.split("rsla");
                p = Pattern.compile("\\{(.*?)\\}");
                m = p.matcher(loca[1]);
                while (m.find()) {
                    locationParamsCommaSeperated=m.group(1);
                }
                locationParams=locationParamsCommaSeperated.split(",");
                //Send localswanremoteact to remote device to start this condition
                remoteDev.postMessageToDevice(getApplicationContext(), "remoteswanlocalact", expression, trigger, locationParams[1],locationParams[0]);
                break;

            case "fullyremote" :
                one= expression.split("THEN");
                three=one[1].split("\\@");
                String actuatorLocation="";
                actLocation=three[0];
                loca=actLocation.split("remote");
                p = Pattern.compile("\\{(.*?)\\}");
                m = p.matcher(loca[1]);
                while (m.find()) {
                    actuatorLocation=m.group(1);
                }
                expressionModified=one[0]+"THEN"+"self"+"@"+three[1];
                //Send localswanremoteact to remote device to start this condition
                remoteDev.postMessageToDevice(getApplicationContext(),"fullyremote", expressionModified, trigger, actuatorLocation,actuatorLocation);
                break;

            case "local" :
                loadConfig(expression,trigger,actuatorType,0,1);
                break;

            case "localswanremoteact" :
                one= expression.split("THEN");
                three=one[1].split("\\@");
                actLocation=three[0];
                loca=actLocation.split("lsra");
                p = Pattern.compile("\\{(.*?)\\}");
                m = p.matcher(loca[1]);
                while (m.find()) {
                    locationParamsCommaSeperated=m.group(1);
                }
                locationParams=locationParamsCommaSeperated.split(",");
                //Send localswanremoteact to remote device to start this condition
                expressionModified=one[0]+"THEN"+"rsla{"+locationParams[1]+","+locationParams[0]+"}"+"@"+three[1];
                remoteDev.postMessageToDevice(getApplicationContext(), "remoteswanlocalact", expressionModified, trigger, locationParams[0],locationParams[1]);
        }
    }

    /**
     *
     * @param expression the expression SWAN+SWAN ACT
     * @param trigger the expression ID
     * @param actuationType Not requried
     * @param remoteoption Not required
     * @param remoteactuatorId Not required
     */
    public void loadConfig(String expression, final String trigger, String actuationType, int remoteoption, int remoteactuatorId){
        ActuatorSong as=new ActuatorSong();
        as.expressionParse(expression);

        final ArrayList<Actuator> actuators = Actuator.getActuators();
        String[] actuatorDescriptions = new String[actuators.size()];
        String actuator=as.valuePath;
        String options=as.option;
        Integer option=0;
        Integer actuatorId=1;

        expressionId=as.expression;

        int typeId=0;
        String trial="";
        for (int i=0; i< actuators.size(); i++) {
            trial = actuators.get(i).getActuator();
            if (trial.compareTo(actuator) == 0) {
                typeId=i;
            } else {
            }
        }

        String Actuator=actuators.get(typeId).getActuator();
        String act=actuator;
        actuatorId=actuators.get(typeId).getId();
        String []listofoption=actuators.get(typeId).getOptions();

        for(int l=0;l<listofoption.length;l++){
            if(listofoption[l].contentEquals(options)){
                option=l;
            }
        }

        final SwanExp newSwanActExp=new SwanExp(as.expression,actuationType,expression,selfId,as.deviceId,as.parameters,trigger);

        newSwanActExp.setName(expression);
        mExpressionView.create(newSwanActExp);

        SwanActRule swanActRule = new SwanActRule();
        swanActRule.setPuck(newSwanActExp);
        swanActRule.setTrigger(trigger);

        Action action = new Action(actuatorId, null);

        String arguments = Action.jsonStringBuilder(actuator, option);
        action.setArguments(arguments);
        swanActRule.addAction(action);

        mActionManager.create(action);
        mRuleManager.createOrExtendExisting(swanActRule);

        mExpressionView.requeryData();
        final ArrayList<SwanExp> rules = mSwanExpManager.getAll();
        if(rules.size() >0) {
            PersistableBundleCompat extras = new PersistableBundleCompat();
                    extras.putString("trigger",trigger);
                    extras.putString("expression",expressionId);
                    extras.putString("actuationtype",actuationType);
            int jobId = new JobRequest.Builder(ActuatorJobScheduler.TAG)
                    .setExecutionWindow(1_000L, 2_000L)
                    .setExtras(extras)
                    .build()
                    .schedule();
        }
    }
    public static Boolean requireCharging=TRUE;
    /**
     *
     * @param expression load the expression virtually
     * @param trigger expressionid of expression loader
     * @param actuationType not required
     * @param remoteoption not required
     * @param remoteactuatorId not required
     * Change parameters of actuator to virtual sensor actuator
     * Register locally swan+virtual sensor actuator
     * Register Remotely remoteswan sensor+real actuator
     *
     */
    public void loadvirtualsensor(String expression, final String trigger, String actuationType, String remoteoption, String remoteactuatorId){
        /**
         * Parse location parameters
         */
        String[] swanLocationParse1=expression.split("THEN");
        String[] swanLocationParse=swanLocationParse1[1].split("\\@");
        String swanLocation=swanLocationParse[0];
        String [] locations=swanLocation.split(",");
        String [] expression1=locations[0].split("\\{");
        String [] expression2=locations[1].split("\\}");
        String locationLeft=expression1[1];
        String locationRight=expression2[0];

        ActuatorSong as = new ActuatorSong();
            as.expressionParse(expression);
            final ArrayList<Actuator> actuators = Actuator.getActuators();
            String actuator = as.valuePath;
            String options = as.option;
            Integer option = 0;
            Integer actuatorId = 1;
            expressionId = as.expression;
            int typeId = 0;
            String trial = "";
            for (int i = 0; i < actuators.size(); i++) {
                trial = actuators.get(i).getActuator();
                if (trial.compareTo(actuator) == 0) {
                    typeId = i;
                } else {
                }
            }
            String act = actuator;
            actuatorId = actuators.get(typeId).getId();
            String[] listofoption = actuators.get(typeId).getOptions();
            for (int l = 0; l < listofoption.length; l++) {
                if (listofoption[l].contentEquals(options)) {
                    option = l;
                }
            }
        final SwanExp newSwanActExp = new SwanExp(as.expression, actuationType, expression, locationLeft, locationRight, as.parameters,trigger);
        newSwanActExp.setName(as.expression);
            mExpressionView.create(newSwanActExp);
            SwanActRule swanActRule = new SwanActRule();
            swanActRule.setPuck(newSwanActExp);

            //Potential Problem
            swanActRule.setTrigger(trigger);

            Action action = new Action(200, null);
            //Change
            String arguments = Action.jsonStringBuilder("virtualsensor", 0);
            action.setArguments(arguments);
            swanActRule.addAction(action);
            mActionManager.create(action);
            mRuleManager.createOrExtendExisting(swanActRule);
            mExpressionView.requeryData();
            final ArrayList<SwanExp> rules = mSwanExpManager.getAll();

            //start a virtual sensor
            InitiateVirtualSensor initiateVirtualSensor=new InitiateVirtualSensor();
            //initiateVirtualSensor.postMessageToDevice(this, "createvirtualsensor", expression, trigger, selfId, as.deviceId);
            initiateVirtualSensor.postMessageToDevice(this, "createvirtualsensor", expression, trigger, locationLeft, locationRight);

            int jobId=0;
            if (rules.size() > 0) {
                PersistableBundleCompat extras = new PersistableBundleCompat();
                extras.putString("trigger", trigger);
                extras.putString("expression", expressionId);
                extras.putString("actuationtype", actuationType);
                jobId = new JobRequest.Builder(RemoteDeviceJobScheduler.TAG)
                        .setExecutionWindow(1_000L, 2_000L)
                        .setExtras(extras)
                        .build()
                        .schedule();

            }
    }

    /**
     *
     * @param expression
     * @param trigger
     * @param actuationType
     * @param remoteoption
     * @param remoteactuatorId
     */

    public void createvirtualsensor(String expression, String trigger, String actuationType, String remoteoption, String remoteactuatorId){
            ActuatorSong as = new ActuatorSong();
            as.expressionParse(expression);
            final ArrayList<Actuator> actuators = Actuator.getActuators();
            String[] actuatorDescriptions = new String[actuators.size()];
            String actuator = as.valuePath;
            String options = as.option;
            Integer option = 0;
            Integer actuatorId = 1;

            expressionId = as.expression;
            int typeId = 0;
            String trial = "";

            for (int i = 0; i < actuators.size(); i++) {
                trial = actuators.get(i).getActuator();
                if (trial.compareTo(actuator) == 0) {
                    typeId = i;
                } else {
                }
            }
            String Actuator = actuators.get(typeId).getActuator();

            String act = actuator;
            actuatorId = actuators.get(typeId).getId();
            String[] listofoption = actuators.get(typeId).getOptions();
            for (int l = 0; l < listofoption.length; l++) {
                if (listofoption[l].contentEquals(options)) {
                    option = l;
                }
            }
            String virtualExpression=as.expression;
            final SwanExp newSwanActExp = new SwanExp(virtualExpression, actuationType, expression, selfId, as.deviceId, as.parameters,trigger);
            newSwanActExp.setName(virtualExpression);
            mExpressionView.create(newSwanActExp);
            SwanActRule swanActRule = new SwanActRule();
            swanActRule.setPuck(newSwanActExp);
            trigger=trigger;
            swanActRule.setTrigger(trigger);
            Action action = new Action(actuatorId, null);
            //Change
            String arguments = Action.jsonStringBuilder(actuator,option);
            action.setArguments(arguments);
            swanActRule.addAction(action);
            mActionManager.create(action);
            mRuleManager.createOrExtendExisting(swanActRule);

            mExpressionView.requeryData();

            final ArrayList<SwanExp> rules = mSwanExpManager.getAll();
            if (rules.size() > 0) {
                PersistableBundleCompat extras = new PersistableBundleCompat();
                extras.putString("trigger", trigger);
                extras.putString("expression", virtualExpression);
                extras.putString("actuationtype", actuationType);
                int jobId = new JobRequest.Builder(VirtualSensorCreatorScheduler.TAG)
                        .setExecutionWindow(1_000L, 2_000L)
                        .setExtras(extras)
                        .build()
                        .schedule();
            }
    }
    /**
     *
     * @param uid
     * @param googleAccount
     */
    public void postDeviceInfo(final String uid,final String googleAccount){
        mRequestQueue= Volley.newRequestQueue(this);
        String url = "https://swan-exp-eval.herokuapp.com/registerdevice";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Device Registered Successfully", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String erro = "volley error";
                        Log.d("Error.Response", erro);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String firebTok = "null";
                while (firebTok.contentEquals("null")) {
                    firebTok = FirebaseInstanceId.getInstance().getToken();
                }
                params.put("email", uid);
                params.put("devid", googleAccount);
                params.put("fbase", firebTok);
                return params;
            }
        };
        mRequestQueue.add(postRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
