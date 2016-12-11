/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/rm/Desktop/swanwithactstudio/phone/src/main/aidl/interdroid/actuators/IActuatorAidl.aidl
 */
package interdroid.actuators;
// Declare any non-default types here with import statements

public interface IActuatorAidl extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements interdroid.actuators.IActuatorAidl
{
private static final java.lang.String DESCRIPTOR = "interdroid.actuators.IActuatorAidl";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an interdroid.actuators.IActuatorAidl interface,
 * generating a proxy if needed.
 */
public static interdroid.actuators.IActuatorAidl asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof interdroid.actuators.IActuatorAidl))) {
return ((interdroid.actuators.IActuatorAidl)iin);
}
return new interdroid.actuators.IActuatorAidl.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getPid:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPid();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_register:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
java.lang.String _arg4;
_arg4 = data.readString();
java.lang.String _arg5;
_arg5 = data.readString();
java.lang.String _arg6;
_arg6 = data.readString();
this.register(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
reply.writeNoException();
return true;
}
case TRANSACTION_registerExpression:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
this.registerExpression(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements interdroid.actuators.IActuatorAidl
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public int getPid() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPid, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public void register(java.lang.String deviceId, int anInt, java.lang.String actuator, java.lang.String options, java.lang.String describeActuator, java.lang.String packageName, java.lang.String serviceName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(deviceId);
_data.writeInt(anInt);
_data.writeString(actuator);
_data.writeString(options);
_data.writeString(describeActuator);
_data.writeString(packageName);
_data.writeString(serviceName);
mRemote.transact(Stub.TRANSACTION_register, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void registerExpression(java.lang.String expression, java.lang.String trigger, java.lang.String actuator, int option, int actuatorId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(expression);
_data.writeString(trigger);
_data.writeString(actuator);
_data.writeInt(option);
_data.writeInt(actuatorId);
mRemote.transact(Stub.TRANSACTION_registerExpression, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getPid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_register = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_registerExpression = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public int getPid() throws android.os.RemoteException;
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void register(java.lang.String deviceId, int anInt, java.lang.String actuator, java.lang.String options, java.lang.String describeActuator, java.lang.String packageName, java.lang.String serviceName) throws android.os.RemoteException;
public void registerExpression(java.lang.String expression, java.lang.String trigger, java.lang.String actuator, int option, int actuatorId) throws android.os.RemoteException;
}
