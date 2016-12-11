/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/rm/Desktop/swanwithactstudio/phone/src/main/aidl/interdroid/swan/sensors/IActuatorSwan.aidl
 */
package interdroid.swan.sensors;
// Declare any non-default types here with import statements

public interface IActuatorSwan extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements interdroid.swan.sensors.IActuatorSwan
{
private static final java.lang.String DESCRIPTOR = "interdroid.swan.sensors.IActuatorSwan";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an interdroid.swan.sensors.IActuatorSwan interface,
 * generating a proxy if needed.
 */
public static interdroid.swan.sensors.IActuatorSwan asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof interdroid.swan.sensors.IActuatorSwan))) {
return ((interdroid.swan.sensors.IActuatorSwan)iin);
}
return new interdroid.swan.sensors.IActuatorSwan.Stub.Proxy(obj);
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
case TRANSACTION_getActuationParameters:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
this.getActuationParameters(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements interdroid.swan.sensors.IActuatorSwan
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
@Override public void getActuationParameters(java.lang.String expression, java.lang.String type, java.lang.String parameters) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(expression);
_data.writeString(type);
_data.writeString(parameters);
mRemote.transact(Stub.TRANSACTION_getActuationParameters, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getActuationParameters = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void getActuationParameters(java.lang.String expression, java.lang.String type, java.lang.String parameters) throws android.os.RemoteException;
}
