/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/rm/Desktop/swanwithactstudio/senseandroidlibrary/src/main/aidl/nl/sense_os/service/ISenseServiceCallback.aidl
 */
package nl.sense_os.service;
public interface ISenseServiceCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements nl.sense_os.service.ISenseServiceCallback
{
private static final java.lang.String DESCRIPTOR = "nl.sense_os.service.ISenseServiceCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an nl.sense_os.service.ISenseServiceCallback interface,
 * generating a proxy if needed.
 */
public static nl.sense_os.service.ISenseServiceCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof nl.sense_os.service.ISenseServiceCallback))) {
return ((nl.sense_os.service.ISenseServiceCallback)iin);
}
return new nl.sense_os.service.ISenseServiceCallback.Stub.Proxy(obj);
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
case TRANSACTION_statusReport:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.statusReport(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onChangeLoginResult:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onChangeLoginResult(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onRegisterResult:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onRegisterResult(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements nl.sense_os.service.ISenseServiceCallback
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
@Override public void statusReport(int status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(status);
mRemote.transact(Stub.TRANSACTION_statusReport, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Callback for change login requests.
     * 
     * @param result 
     *            0 if login completed successfully, -2 if login was forbidden, and -1 for any other errors.
     */
@Override public void onChangeLoginResult(int result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(result);
mRemote.transact(Stub.TRANSACTION_onChangeLoginResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Callback for registration requests.
     * 
     * @param result 
     *            0 if registration completed successfully, -2 if the username is already taken, and -1 for any other errors.
     */
@Override public void onRegisterResult(int result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(result);
mRemote.transact(Stub.TRANSACTION_onRegisterResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_statusReport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onChangeLoginResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onRegisterResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void statusReport(int status) throws android.os.RemoteException;
/**
     * Callback for change login requests.
     * 
     * @param result 
     *            0 if login completed successfully, -2 if login was forbidden, and -1 for any other errors.
     */
public void onChangeLoginResult(int result) throws android.os.RemoteException;
/**
     * Callback for registration requests.
     * 
     * @param result 
     *            0 if registration completed successfully, -2 if the username is already taken, and -1 for any other errors.
     */
public void onRegisterResult(int result) throws android.os.RemoteException;
}
