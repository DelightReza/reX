package android_utils;

import com.chaquo.python.PyCtorMarker;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.StaticProxy;

/* renamed from: android_utils.R */
/* loaded from: classes.dex */
public class RunnableC0013R implements Runnable, StaticProxy {
    private PyObject _chaquopyDict;

    static {
        Python.getInstance().getModule("android_utils").get((Object) "R");
    }

    public RunnableC0013R() {
        PyObject pyObject_chaquopyCall = PyObject._chaquopyCall(this, "__init__", new Object[0]);
        if (pyObject_chaquopyCall != null) {
            pyObject_chaquopyCall.toJava(Void.TYPE);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        PyObject pyObject_chaquopyCall = PyObject._chaquopyCall(this, "run", new Object[0]);
        if (pyObject_chaquopyCall != null) {
            pyObject_chaquopyCall.toJava(Void.TYPE);
        }
    }

    public RunnableC0013R(PyCtorMarker pyCtorMarker) {
    }

    @Override // com.chaquo.python.PyProxy
    public PyObject _chaquopyGetDict() {
        return this._chaquopyDict;
    }

    @Override // com.chaquo.python.PyProxy
    public void _chaquopySetDict(PyObject pyObject) {
        this._chaquopyDict = pyObject;
    }
}
