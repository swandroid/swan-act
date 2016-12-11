/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/rm/Desktop/swanwithactstudio/phone/src/main/aidl/interdroid/swan/sensors/Sensor.aidl
 */
package interdroid.swan.sensors;
public interface Sensor extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements interdroid.swan.sensors.Sensor
{
private static final java.lang.String DESCRIPTOR = "interdroid.swan.sensors.Sensor";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an interdroid.swan.sensors.Sensor interface,
 * generating a proxy if needed.
 */
public static interdroid.swan.sensors.Sensor asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof interdroid.swan.sensors.Sensor))) {
return ((interdroid.swan.sensors.Sensor)iin);
}
return new interdroid.swan.sensors.Sensor.Stub.Proxy(obj);
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
case TRANSACTION_register:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
android.os.Bundle _arg2;
if ((0!=data.readInt())) {
_arg2 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
android.os.Bundle _arg3;
if ((0!=data.readInt())) {
_arg3 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
android.os.Bundle _arg4;
if ((0!=data.readInt())) {
_arg4 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg4 = null;
}
this.register(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
case TRANSACTION_unregister:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.unregister(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getValues:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
long _arg1;
_arg1 = data.readLong();
long _arg2;
_arg2 = data.readLong();
java.util.List<interdroid.swancore.swansong.TimestampedValue> _result = this.getValues(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getStartUpTime:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
long _result = this.getStartUpTime(_arg0);
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getInfo:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _result = this.getInfo();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements interdroid.swan.sensors.Sensor
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
@Override public void register(java.lang.String id, java.lang.String valuePath, android.os.Bundle configuration, android.os.Bundle httpConfiguration, android.os.Bundle extraConfiguration) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(id);
_data.writeString(valuePath);
if ((configuration!=null)) {
_data.writeInt(1);
configuration.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((httpConfiguration!=null)) {
_data.writeInt(1);
httpConfiguration.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((extraConfiguration!=null)) {
_data.writeInt(1);
extraConfiguration.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_register, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregister(java.lang.String id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(id);
mRemote.transact(Stub.TRANSACTION_unregister, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.util.List<interdroid.swancore.swansong.TimestampedValue> getValues(java.lang.String id, long now, long timespan) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<interdroid.swancore.swansong.TimestampedValue> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(id);
_data.writeLong(now);
_data.writeLong(timespan);
mRemote.transact(Stub.TRANSACTION_getValues, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(interdroid.swancore.swansong.TimestampedValue.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public long getStartUpTime(java.lang.String id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(id);
mRemote.transact(Stub.TRANSACTION_getStartUpTime, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.os.Bundle getInfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.Bundle _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.os.Bundle.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_register = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregister = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getValues = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getStartUpTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void register(java.lang.String id, java.lang.String valuePath, android.os.Bundle configuration, android.os.Bundle httpConfiguration, android.os.Bundle extraConfiguration) throws android.os.RemoteException;
public void unregister(java.lang.String id) throws android.os.RemoteException;
public java.util.List<interdroid.swancore.swansong.TimestampedValue> getValues(java.lang.String id, long now, long timespan) throws android.os.RemoteException;
public long getStartUpTime(java.lang.String id) throws android.os.RemoteException;
public android.os.Bundle getInfo() throws android.os.RemoteException;
}
