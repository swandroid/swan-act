package interdroid.swan.actuators;

/**
 * Created by rm on 27/10/2016.
 */

public class Backup {

    //EventBus.postEvent(Actuate.NEW_EXPRESSION_DETECTED);
    //socketIo.initSpam();
        /*final SocketIo socketIo=new SocketIo();
        new Thread(new Runnable() {
            public void run() {
                socketIo.connect();
                for(int i=0;i<10;i++) {
                    socketIo.attemptSend(1);
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                socketIo.connect();
                for(int i=0;i<10;i++) {
                    socketIo.attemptSend(2);
                }
            }
        }).start();*/
    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
        /*String trialExpression="self@light:lux{ANY,10ms}<10.0THENself@ThingsSpeak:put&&self@vibrator:vibrate";
        CheckMoreThanOneActuator checkMoreThanOneActuator=new CheckMoreThanOneActuator();
        */

        /*TestSwan ts=new TestSwan();
        ts.swanService(this);*/
    //TestMainSwan ts2=new TestMainSwan();
    //ts2.swanService(this);

        /*String exp1="self@light:lux{ANY,10ms}>10.0THEN";
        String exp2="self@ExpressionActuator:expression?expression=\"self@movement:x{ANY,10ms}>1.0THENself@vibrator:vibrate\"";
        String exp3="&&self@ExpressionActuator:expression?expression=\"self@movement:y{ANY,10ms}>1.0THENself@socket:post\"";
        String myExp=exp1+exp2+exp3;*/
    //loadConfig(myExp,"ddf","local",1,2);

    //FEEDBACK
        /*loadConfig("self@light:lux{ANY,10ms}<100.0THENself@vibrator:vibrate","test1","local",1,2);
        String trialExpression="self@movement:x{ANY,10ms}>1.0THENself@feedback:before?expression=\"self@light:lux{ANY,10ms}<100.0\"&window=\"ANY,100ms\"&comparator=\"lol\"";
        loadConfig(trialExpression,"test3","local",1,2);*/

    //check more than one operation
        /*String trialExpression="self@light:lux{ANY,10ms}<10.0THENself@socket2:put";
        CheckMoreThanOneActuator checkMoreThanOneActuator=new CheckMoreThanOneActuator();
        String[] expressionsToExecute=checkMoreThanOneActuator.check(trialExpression);
        for(int k=0;k<expressionsToExecute.length;k++){
            Log.d("Expression:",expressionsToExecute[k]);
            //Thread each executor
            Integer randomId = rand.nextInt((5000 - 0) + 1) + 0;
            Log.d("Random Id",randomId.toString());
            loadConfig(expressionsToExecute[k],randomId.toString(),"local",1,2);
        }*/






    //Authentication authentication=new Authentication();

        /*new Thread() {
            @Override
            public void run() {
                loadConfig("self@light:lux{ANY,10ms}<10.0THENself@socket:post", "test1", "local", 1, 2);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                loadConfig("self@battery:level{MAX,1h}>25THENself@socket:post","test2","local",1,2);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                loadConfig("self@movement:y{ANY,10ms}>1.0THENself@socket:post","test3","local",1,2);
            }
        }.start();*/

    //loadConfig("self@light:lux{ANY,10ms}<10.0THENself@socket2:put","test1","local",1,2);
    //loadConfig("self@light:lux{ANY,10ms}<10.0THENself@socket2:put","test2","local",1,2);

//        loadConfig("self@movement:x{ANY,10ms}>25.0THENself@socket:post","test2","local",1,2);
    //      loadConfig("self@movement:y{ANY,10ms}>1.0THENself@socket:post","test3","local",1,2);
    //    loadConfig("self@movement:z{ANY,10ms}>1.0THENself@socket:post","test4","local",1,2);


    //loadConfig("self@movement:x{ANY,10ms}>0.0THENself@socket:post","test2","local",1,2);
    //loadConfig("self@light:lux{ANY,10ms}>10.0THENself@socket:post","test3","local",1,2);

    //loadConfig("self@light:lux{ANY,10ms}<10.0THENself@ExpressionActuator:expression?expression=\"self@movement:x{ANY,10ms}>1.0THENself@vibrator:vibrate\"","locslt","local",1,2);


    //for(int i=0;i<2;i++) {

    //loadConfig("self@light:lux{ANY,10ms}>5.0THENself@socket:post&&self:vibrator:vibrate", "locslt", "local", 1, 2);

        /*ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        double availableMegs = mi.availMem / 1048576L;*/

    //LocalConfig localConfig=new LocalConfig(this,selfId,mActionManager,mPuckAdapter,mRuleManager);

    //loadConfig("self@light:lux{ANY,10ms}<10.0THENself@socket:post", "locsle", "local", 1, 2);


    // loadConfig("self@battery:level{MAX,1h}-self@battery:level{MIN,1h}<25THEN@vibrator:vibrate", "locsl", "local", 1, 2);


    //loadConfig("self@light:lux{ANY,10ms}<10.0THENself@socket:post", "locsler", "local", 1, 2);

    //loadConfig("self@movement:y{ANY,10ms}<1.0THENself@socket:post", "locslw"+i, "local", 1, 2);
    //loadConfig("self@movement:z{ANY,10ms}<1.0THENself@socket:post", "locslq"+i, "local", 1, 2);

    //}
    //loadConfig("self@light:lux{ANY,10ms}<10.0THENself@vibrator:vibrate?duration=\"500\"","locslt","local",1,2);


    //loadConfig("self@light:lux{ANY,10ms}<10.0THENself@socket2:put","locadsttor","local",1,2);
    //expressionRegistration("self@light:lux{ANY,10ms}<10.0THENself@vibrator:vibrate","localactuator","localswanremoteact",1,2);

    //loadConfig("self@light:lux{ANY,10ms}>50.0THENself@ringer:vibrateonly","localactuator","local",1,2);
    ///Test Performance
       /* Socket mSocket;
        try {
            mSocket = IO.socket( "https://swan-exp-eval.herokuapp.com");
            mSocket.connect();
            mSocket.on("Reply",onReply);
        } catch (URISyntaxException e) {}*/



    ///
       /* private void unregisterSWANSensor(){
       for ( int key : swanExpressionTracker.expressions.keySet() ) {
            ExpressionManager.unregisterExpression(swanExpressionTracker.expressions.get(key), String.valueOf(key));
        }
    }*/




    ////loadConifg

    //Notification on actuation registered
           /* NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_launcher_swan)
                            .setContentTitle("Actuator Triggered")
                            .setContentText("Actuator:"+actuator);

            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();
            String[] events = new String[6];
            events[0]="Expression: "+as.expression;
            events[1]="Value_Path: "+as.valuePath;
            inboxStyle.setBigContentTitle("Actuation Details:");
            for (int i=0; i < events.length; i++) {

                inboxStyle.addLine(events[i]);
            }
            mBuilder.setStyle(inboxStyle);


            Intent resultIntent = new Intent(this, ActuatorManager.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(ActuatorManager.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
            int mId=1;
            mNotificationManager.notify(mId, mBuilder.build());*/

           /* new Thread() {
                @Override
                public void run() {*/


    //// Interesting case
    /*for (int i=0; i< actuators.size(); i++) {
            String getActuator=actuators.get(i).getActuator();
            if(getActuator.contentEquals(actuator));
            {
                actuatorId=actuators.get(i).getId();
                String []listofoption=actuators.get(i).getOptions();
                Log.d("listofoptions",listofoption[1]);
                for(int l=0;l<listofoption.length;l++){
                    Log.d("listofoptions",listofoption[l]);
                    Log.d("options",options);
                    if(listofoption[l].contentEquals(options)){
                        option=l;
                    }
                }
            }
        }*/

}
