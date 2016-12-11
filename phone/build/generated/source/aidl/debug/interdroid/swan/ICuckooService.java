/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/rm/Desktop/swanwithactstudio/phone/src/main/aidl/interdroid/swan/ICuckooService.aidl
 */
package interdroid.swan;
/** Add method to be offloaded here
*   Add local implementation in interdroid.cuckoo.CuckooServiceLocal service
*   Add remote implementation in interdroid.swan.cuckoo.remote.CuckooServiceRemote service
*/
public interface ICuckooService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements interdroid.swan.ICuckooService
{
private static final java.lang.String DESCRIPTOR = "interdroid.swan.ICuckooService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an interdroid.swan.ICuckooService interface,
 * generating a proxy if needed.
 */
public static interdroid.swan.ICuckooService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof interdroid.swan.ICuckooService))) {
return ((interdroid.swan.ICuckooService)iin);
}
return new interdroid.swan.ICuckooService.Stub.Proxy(obj);
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
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements interdroid.swan.ICuckooService
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
}
}
}
