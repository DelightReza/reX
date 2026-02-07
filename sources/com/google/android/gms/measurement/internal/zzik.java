package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.chaquo.python.internal.Common;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.measurement.zzos;
import com.google.android.gms.internal.measurement.zzph;
import com.google.android.gms.internal.measurement.zzqu;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public final class zzik extends zzf {
    protected zzij zza;
    final zzs zzb;
    protected boolean zzc;
    private zzhf zzd;
    private final Set zze;
    private boolean zzf;
    private final AtomicReference zzg;
    private final Object zzh;
    private zzhb zzi;
    private final AtomicLong zzj;
    private long zzk;
    private final zzlo zzl;

    protected zzik(zzgd zzgdVar) {
        super(zzgdVar);
        this.zze = new CopyOnWriteArraySet();
        this.zzh = new Object();
        this.zzc = true;
        this.zzl = new zzhz(this);
        this.zzg = new AtomicReference();
        this.zzi = zzhb.zza;
        this.zzk = -1L;
        this.zzj = new AtomicLong(0L);
        this.zzb = new zzs(zzgdVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzaa(Boolean bool, boolean z) {
        zzg();
        zza();
        this.zzt.zzaA().zzc().zzb("Setting app measurement enabled (FE)", bool);
        this.zzt.zzm().zzh(bool);
        if (z) {
            zzfi zzfiVarZzm = this.zzt.zzm();
            zzgd zzgdVar = zzfiVarZzm.zzt;
            zzfiVarZzm.zzg();
            SharedPreferences.Editor editorEdit = zzfiVarZzm.zza().edit();
            if (bool != null) {
                editorEdit.putBoolean("measurement_enabled_from_api", bool.booleanValue());
            } else {
                editorEdit.remove("measurement_enabled_from_api");
            }
            editorEdit.apply();
        }
        if (this.zzt.zzK() || !(bool == null || bool.booleanValue())) {
            zzab();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzab() {
        zzik zzikVar;
        zzg();
        String strZza = this.zzt.zzm().zzh.zza();
        if (strZza == null) {
            zzikVar = this;
        } else if ("unset".equals(strZza)) {
            zzikVar = this;
            zzikVar.zzY(Common.ASSET_APP, "_npa", null, this.zzt.zzax().currentTimeMillis());
        } else {
            zzikVar = this;
            zzikVar.zzY(Common.ASSET_APP, "_npa", Long.valueOf(true != "true".equals(strZza) ? 0L : 1L), zzikVar.zzt.zzax().currentTimeMillis());
        }
        if (!zzikVar.zzt.zzJ() || !zzikVar.zzc) {
            zzikVar.zzt.zzaA().zzc().zza("Updating Scion state (FE)");
            zzikVar.zzt.zzt().zzI();
            return;
        }
        zzikVar.zzt.zzaA().zzc().zza("Recording app launch after enabling measurement for the first time (FE)");
        zzz();
        zzph.zzc();
        if (zzikVar.zzt.zzf().zzs(null, zzeg.zzaf)) {
            zzikVar.zzt.zzu().zza.zza();
        }
        zzikVar.zzt.zzaB().zzp(new zzhn(this));
    }

    static /* bridge */ /* synthetic */ void zzv(zzik zzikVar, zzhb zzhbVar, zzhb zzhbVar2) {
        boolean z;
        zzha[] zzhaVarArr = {zzha.ANALYTICS_STORAGE, zzha.AD_STORAGE};
        int i = 0;
        while (true) {
            if (i >= 2) {
                z = false;
                break;
            }
            zzha zzhaVar = zzhaVarArr[i];
            if (!zzhbVar2.zzj(zzhaVar) && zzhbVar.zzj(zzhaVar)) {
                z = true;
                break;
            }
            i++;
        }
        boolean zZzn = zzhbVar.zzn(zzhbVar2, zzha.ANALYTICS_STORAGE, zzha.AD_STORAGE);
        if (z || zZzn) {
            zzikVar.zzt.zzh().zzo();
        }
    }

    static /* synthetic */ void zzw(zzik zzikVar, zzhb zzhbVar, long j, boolean z, boolean z2) {
        zzikVar.zzg();
        zzikVar.zza();
        zzhb zzhbVarZzc = zzikVar.zzt.zzm().zzc();
        if (j <= zzikVar.zzk && zzhb.zzk(zzhbVarZzc.zza(), zzhbVar.zza())) {
            zzikVar.zzt.zzaA().zzi().zzb("Dropped out-of-date consent setting, proposed settings", zzhbVar);
            return;
        }
        zzfi zzfiVarZzm = zzikVar.zzt.zzm();
        zzgd zzgdVar = zzfiVarZzm.zzt;
        zzfiVarZzm.zzg();
        int iZza = zzhbVar.zza();
        if (!zzfiVarZzm.zzl(iZza)) {
            zzikVar.zzt.zzaA().zzi().zzb("Lower precedence consent source ignored, proposed source", Integer.valueOf(zzhbVar.zza()));
            return;
        }
        SharedPreferences.Editor editorEdit = zzfiVarZzm.zza().edit();
        editorEdit.putString("consent_settings", zzhbVar.zzi());
        editorEdit.putInt("consent_source", iZza);
        editorEdit.apply();
        zzikVar.zzk = j;
        zzikVar.zzt.zzt().zzF(z);
        if (z2) {
            zzikVar.zzt.zzt().zzu(new AtomicReference());
        }
    }

    public final void zzA(String str, String str2, Bundle bundle) {
        long jCurrentTimeMillis = this.zzt.zzax().currentTimeMillis();
        Preconditions.checkNotEmpty(str);
        Bundle bundle2 = new Bundle();
        bundle2.putString("name", str);
        bundle2.putLong("creation_timestamp", jCurrentTimeMillis);
        if (str2 != null) {
            bundle2.putString("expired_event_name", str2);
            bundle2.putBundle("expired_event_params", bundle);
        }
        this.zzt.zzaB().zzp(new zzhu(this, bundle2));
    }

    public final void zzB() {
        if (!(this.zzt.zzaw().getApplicationContext() instanceof Application) || this.zza == null) {
            return;
        }
        ((Application) this.zzt.zzaw().getApplicationContext()).unregisterActivityLifecycleCallbacks(this.zza);
    }

    final /* synthetic */ void zzC(Bundle bundle) {
        if (bundle == null) {
            this.zzt.zzm().zzs.zzb(new Bundle());
            return;
        }
        Bundle bundleZza = this.zzt.zzm().zzs.zza();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj != null && !(obj instanceof String) && !(obj instanceof Long) && !(obj instanceof Double)) {
                if (this.zzt.zzv().zzag(obj)) {
                    this.zzt.zzv().zzO(this.zzl, null, 27, null, null, 0);
                }
                this.zzt.zzaA().zzl().zzc("Invalid default event parameter type. Name, value", str, obj);
            } else if (zzlp.zzaj(str)) {
                this.zzt.zzaA().zzl().zzb("Invalid default event parameter name. Name", str);
            } else if (obj == null) {
                bundleZza.remove(str);
            } else {
                zzlp zzlpVarZzv = this.zzt.zzv();
                this.zzt.zzf();
                if (zzlpVarZzv.zzab("param", str, 100, obj)) {
                    this.zzt.zzv().zzP(bundleZza, str, obj);
                }
            }
        }
        this.zzt.zzv();
        int iZzc = this.zzt.zzf().zzc();
        if (bundleZza.size() > iZzc) {
            int i = 0;
            for (String str2 : new TreeSet(bundleZza.keySet())) {
                i++;
                if (i > iZzc) {
                    bundleZza.remove(str2);
                }
            }
            this.zzt.zzv().zzO(this.zzl, null, 26, null, null, 0);
            this.zzt.zzaA().zzl().zza("Too many default event parameters set. Discarding beyond event parameter limit");
        }
        this.zzt.zzm().zzs.zzb(bundleZza);
        this.zzt.zzt().zzH(bundleZza);
    }

    public final void zzD(String str, String str2, Bundle bundle) {
        zzE(str, str2, bundle, true, true, this.zzt.zzax().currentTimeMillis());
    }

    public final void zzE(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        Bundle bundle2 = bundle;
        if (str2 == "screen_view" || (str2 != null && str2.equals("screen_view"))) {
            this.zzt.zzs().zzx(bundle2, j);
            return;
        }
        boolean z3 = !z2 || this.zzd == null || zzlp.zzaj(str2);
        if (str == null) {
            str = Common.ASSET_APP;
        }
        zzM(str, str2, j, bundle2, z2, z3, z, null);
    }

    public final void zzF(String str, String str2, Bundle bundle, String str3) {
        zzgd.zzO();
        zzM("auto", str2, this.zzt.zzax().currentTimeMillis(), bundle, false, true, true, str3);
    }

    final void zzG(String str, String str2, Bundle bundle) {
        zzg();
        zzH(str, str2, this.zzt.zzax().currentTimeMillis(), bundle);
    }

    final void zzH(String str, String str2, long j, Bundle bundle) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzg();
        zzI(str, str2, j, bundle, true, this.zzd == null || zzlp.zzaj(str2), true, null);
    }

    protected final void zzI(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        long j2;
        boolean z4;
        long j3;
        char c;
        ArrayList arrayList;
        long j4;
        String str4;
        Bundle[] bundleArr;
        String str5 = str;
        Preconditions.checkNotEmpty(str5);
        Preconditions.checkNotNull(bundle);
        zzg();
        zza();
        if (!this.zzt.zzJ()) {
            this.zzt.zzaA().zzc().zza("Event not sent since app measurement is disabled");
            return;
        }
        List listZzn = this.zzt.zzh().zzn();
        if (listZzn != null && !listZzn.contains(str2)) {
            this.zzt.zzaA().zzc().zzc("Dropping non-safelisted event. event name, origin", str2, str5);
            return;
        }
        if (!this.zzf) {
            this.zzf = true;
            try {
                try {
                    (!this.zzt.zzN() ? Class.forName("com.google.android.gms.tagmanager.TagManagerService", true, this.zzt.zzaw().getClassLoader()) : Class.forName("com.google.android.gms.tagmanager.TagManagerService")).getDeclaredMethod("initialize", Context.class).invoke(null, this.zzt.zzaw());
                } catch (Exception e) {
                    this.zzt.zzaA().zzk().zzb("Failed to invoke Tag Manager's initialize() method", e);
                }
            } catch (ClassNotFoundException unused) {
                this.zzt.zzaA().zzi().zza("Tag Manager is not found and thus will not be used");
            }
        }
        if ("_cmp".equals(str2) && bundle.containsKey("gclid")) {
            this.zzt.zzay();
            zzY("auto", "_lgclid", bundle.getString("gclid"), this.zzt.zzax().currentTimeMillis());
        }
        zzik zzikVar = this;
        zzikVar.zzt.zzay();
        if (z && zzlp.zzan(str2)) {
            zzikVar.zzt.zzv().zzL(bundle, zzikVar.zzt.zzm().zzs.zza());
        }
        if (!z3) {
            zzikVar.zzt.zzay();
            if (!"_iap".equals(str2)) {
                zzlp zzlpVarZzv = zzikVar.zzt.zzv();
                int i = 2;
                if (zzlpVarZzv.zzad("event", str2)) {
                    if (zzlpVarZzv.zzaa("event", zzhc.zza, zzhc.zzb, str2)) {
                        zzlpVarZzv.zzt.zzf();
                        if (zzlpVarZzv.zzZ("event", 40, str2)) {
                            i = 0;
                        }
                    } else {
                        i = 13;
                    }
                }
                if (i != 0) {
                    zzikVar.zzt.zzaA().zze().zzb("Invalid public event name. Event will not be logged (FE)", zzikVar.zzt.zzj().zzd(str2));
                    zzlp zzlpVarZzv2 = zzikVar.zzt.zzv();
                    zzikVar.zzt.zzf();
                    zzikVar.zzt.zzv().zzO(zzikVar.zzl, null, i, "_ev", zzlpVarZzv2.zzD(str2, 40, true), str2 != null ? str2.length() : 0);
                    return;
                }
            }
        }
        zzikVar.zzt.zzay();
        zzir zzirVarZzj = zzikVar.zzt.zzs().zzj(false);
        if (zzirVarZzj != null && !bundle.containsKey("_sc")) {
            zzirVarZzj.zzd = true;
        }
        zzlp.zzK(zzirVarZzj, bundle, z && !z3);
        boolean zEquals = "am".equals(str5);
        boolean zZzaj = zzlp.zzaj(str2);
        if (!z || zzikVar.zzd == null || zZzaj) {
            j2 = j;
            z4 = zEquals;
        } else {
            if (!zEquals) {
                zzikVar.zzt.zzaA().zzc().zzc("Passing event to registered event handler (FE)", zzikVar.zzt.zzj().zzd(str2), zzikVar.zzt.zzj().zzb(bundle));
                Preconditions.checkNotNull(zzikVar.zzd);
                zzikVar.zzd.interceptEvent(str5, str2, bundle, j);
                return;
            }
            j2 = j;
            z4 = true;
        }
        if (zzikVar.zzt.zzM()) {
            int iZzh = zzikVar.zzt.zzv().zzh(str2);
            if (iZzh != 0) {
                zzikVar.zzt.zzaA().zze().zzb("Invalid event name. Event will not be logged (FE)", zzikVar.zzt.zzj().zzd(str2));
                zzlp zzlpVarZzv3 = zzikVar.zzt.zzv();
                zzikVar.zzt.zzf();
                zzikVar.zzt.zzv().zzO(zzikVar.zzl, str3, iZzh, "_ev", zzlpVarZzv3.zzD(str2, 40, true), str2 != null ? str2.length() : 0);
                return;
            }
            Bundle bundleZzu = zzikVar.zzt.zzv().zzu(str3, str2, bundle, CollectionUtils.listOf((Object[]) new String[]{"_o", "_sn", "_sc", "_si"}), z3);
            Preconditions.checkNotNull(bundleZzu);
            zzikVar.zzt.zzay();
            if (zzikVar.zzt.zzs().zzj(false) == null || !"_ae".equals(str2)) {
                j3 = 0;
                c = 0;
            } else {
                zzkn zzknVar = zzikVar.zzt.zzu().zzb;
                j3 = 0;
                long jElapsedRealtime = zzknVar.zzc.zzt.zzax().elapsedRealtime();
                c = 0;
                long j5 = jElapsedRealtime - zzknVar.zzb;
                zzknVar.zzb = jElapsedRealtime;
                if (j5 > 0) {
                    zzikVar.zzt.zzv().zzI(bundleZzu, j5);
                }
            }
            zzos.zzc();
            if (zzikVar.zzt.zzf().zzs(null, zzeg.zzae)) {
                if (!"auto".equals(str5) && "_ssr".equals(str2)) {
                    zzlp zzlpVarZzv4 = zzikVar.zzt.zzv();
                    String string = bundleZzu.getString("_ffr");
                    if (Strings.isEmptyOrWhitespace(string)) {
                        string = null;
                    } else if (string != null) {
                        string = string.trim();
                    }
                    if (zzln.zza(string, zzlpVarZzv4.zzt.zzm().zzp.zza())) {
                        zzlpVarZzv4.zzt.zzaA().zzc().zza("Not logging duplicate session_start_with_rollout event");
                        return;
                    }
                    zzlpVarZzv4.zzt.zzm().zzp.zzb(string);
                } else if ("_ae".equals(str2)) {
                    String strZza = zzikVar.zzt.zzv().zzt.zzm().zzp.zza();
                    if (!TextUtils.isEmpty(strZza)) {
                        bundleZzu.putString("_ffr", strZza);
                    }
                }
            }
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(bundleZzu);
            boolean zZzo = zzikVar.zzt.zzf().zzs(null, zzeg.zzaG) ? zzikVar.zzt.zzu().zzo() : zzikVar.zzt.zzm().zzm.zzb();
            if (zzikVar.zzt.zzm().zzj.zza() > j3 && zzikVar.zzt.zzm().zzk(j2) && zZzo) {
                zzikVar.zzt.zzaA().zzj().zza("Current session is expired, remove the session number, ID, and engagement time");
                long j6 = j3;
                arrayList = arrayList2;
                j4 = j6;
                str4 = "_ae";
                zzY("auto", "_sid", null, zzikVar.zzt.zzax().currentTimeMillis());
                zzY("auto", "_sno", null, this.zzt.zzax().currentTimeMillis());
                zzY("auto", "_se", null, this.zzt.zzax().currentTimeMillis());
                zzikVar = this;
                zzikVar.zzt.zzm().zzk.zzb(j4);
            } else {
                long j7 = j3;
                arrayList = arrayList2;
                j4 = j7;
                str4 = "_ae";
            }
            if (bundleZzu.getLong("extend_session", j4) == 1) {
                zzikVar.zzt.zzaA().zzj().zza("EXTEND_SESSION param attached: initiate a new session or extend the current active session");
                zzikVar.zzt.zzu().zza.zzb(j2, true);
            }
            ArrayList arrayList3 = new ArrayList(bundleZzu.keySet());
            Collections.sort(arrayList3);
            int size = arrayList3.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str6 = (String) arrayList3.get(i2);
                if (str6 != null) {
                    zzikVar.zzt.zzv();
                    Object obj = bundleZzu.get(str6);
                    if (obj instanceof Bundle) {
                        bundleArr = new Bundle[1];
                        bundleArr[c] = (Bundle) obj;
                    } else if (obj instanceof Parcelable[]) {
                        Parcelable[] parcelableArr = (Parcelable[]) obj;
                        bundleArr = (Bundle[]) Arrays.copyOf(parcelableArr, parcelableArr.length, Bundle[].class);
                    } else if (obj instanceof ArrayList) {
                        ArrayList arrayList4 = (ArrayList) obj;
                        bundleArr = (Bundle[]) arrayList4.toArray(new Bundle[arrayList4.size()]);
                    } else {
                        bundleArr = null;
                    }
                    if (bundleArr != null) {
                        bundleZzu.putParcelableArray(str6, bundleArr);
                    }
                }
            }
            int i3 = 0;
            while (i3 < arrayList.size()) {
                ArrayList arrayList5 = arrayList;
                Bundle bundleZzt = (Bundle) arrayList5.get(i3);
                String str7 = i3 != 0 ? "_ep" : str2;
                bundleZzt.putString("_o", str5);
                if (z2) {
                    bundleZzt = zzikVar.zzt.zzv().zzt(bundleZzt);
                }
                String str8 = str5;
                Bundle bundle2 = bundleZzt;
                zzikVar.zzt.zzt().zzA(new zzau(str7, new zzas(bundleZzt), str8, j2), str3);
                if (!z4) {
                    Iterator it = zzikVar.zze.iterator();
                    while (it.hasNext()) {
                        ((zzhg) it.next()).onEvent(str, str2, new Bundle(bundle2), j);
                    }
                }
                i3++;
                str5 = str;
                j2 = j;
                arrayList = arrayList5;
            }
            zzikVar.zzt.zzay();
            if (zzikVar.zzt.zzs().zzj(false) == null || !str4.equals(str2)) {
                return;
            }
            zzikVar.zzt.zzu().zzb.zzd(true, true, zzikVar.zzt.zzax().elapsedRealtime());
        }
    }

    public final void zzJ(zzhg zzhgVar) {
        zza();
        Preconditions.checkNotNull(zzhgVar);
        if (this.zze.add(zzhgVar)) {
            return;
        }
        this.zzt.zzaA().zzk().zza("OnEventListener already registered");
    }

    public final void zzK(long j) {
        this.zzg.set(null);
        this.zzt.zzaB().zzp(new zzhs(this, j));
    }

    final void zzL(long j, boolean z) {
        zzg();
        zza();
        this.zzt.zzaA().zzc().zza("Resetting analytics data (FE)");
        zzkp zzkpVarZzu = this.zzt.zzu();
        zzkpVarZzu.zzg();
        zzkpVarZzu.zzb.zza();
        zzqu.zzc();
        if (this.zzt.zzf().zzs(null, zzeg.zzan)) {
            this.zzt.zzh().zzo();
        }
        boolean zZzJ = this.zzt.zzJ();
        zzfi zzfiVarZzm = this.zzt.zzm();
        zzfiVarZzm.zzc.zzb(j);
        if (!TextUtils.isEmpty(zzfiVarZzm.zzt.zzm().zzp.zza())) {
            zzfiVarZzm.zzp.zzb(null);
        }
        zzph.zzc();
        zzag zzagVarZzf = zzfiVarZzm.zzt.zzf();
        zzef zzefVar = zzeg.zzaf;
        if (zzagVarZzf.zzs(null, zzefVar)) {
            zzfiVarZzm.zzj.zzb(0L);
        }
        zzfiVarZzm.zzk.zzb(0L);
        if (!zzfiVarZzm.zzt.zzf().zzv()) {
            zzfiVarZzm.zzi(!zZzJ);
        }
        zzfiVarZzm.zzq.zzb(null);
        zzfiVarZzm.zzr.zzb(0L);
        zzfiVarZzm.zzs.zzb(null);
        if (z) {
            this.zzt.zzt().zzC();
        }
        zzph.zzc();
        if (this.zzt.zzf().zzs(null, zzefVar)) {
            this.zzt.zzu().zza.zza();
        }
        this.zzc = !zZzJ;
    }

    protected final void zzM(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        Bundle bundle2 = new Bundle(bundle);
        for (String str4 : bundle2.keySet()) {
            Object obj = bundle2.get(str4);
            if (obj instanceof Bundle) {
                bundle2.putBundle(str4, new Bundle((Bundle) obj));
            } else {
                int i = 0;
                if (obj instanceof Parcelable[]) {
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    while (i < parcelableArr.length) {
                        Parcelable parcelable = parcelableArr[i];
                        if (parcelable instanceof Bundle) {
                            parcelableArr[i] = new Bundle((Bundle) parcelable);
                        }
                        i++;
                    }
                } else if (obj instanceof List) {
                    List list = (List) obj;
                    while (i < list.size()) {
                        Object obj2 = list.get(i);
                        if (obj2 instanceof Bundle) {
                            list.set(i, new Bundle((Bundle) obj2));
                        }
                        i++;
                    }
                }
            }
        }
        this.zzt.zzaB().zzp(new zzhp(this, str, str2, j, bundle2, z, z2, z3, str3));
    }

    final void zzN(String str, String str2, long j, Object obj) {
        this.zzt.zzaB().zzp(new zzhq(this, str, str2, obj, j));
    }

    final void zzO(String str) {
        this.zzg.set(str);
    }

    public final void zzP(Bundle bundle) {
        zzQ(bundle, this.zzt.zzax().currentTimeMillis());
    }

    public final void zzQ(Bundle bundle, long j) {
        Preconditions.checkNotNull(bundle);
        Bundle bundle2 = new Bundle(bundle);
        if (!TextUtils.isEmpty(bundle2.getString("app_id"))) {
            this.zzt.zzaA().zzk().zza("Package name should be null when calling setConditionalUserProperty");
        }
        bundle2.remove("app_id");
        Preconditions.checkNotNull(bundle2);
        zzgz.zza(bundle2, "app_id", String.class, null);
        zzgz.zza(bundle2, "origin", String.class, null);
        zzgz.zza(bundle2, "name", String.class, null);
        zzgz.zza(bundle2, "value", Object.class, null);
        zzgz.zza(bundle2, "trigger_event_name", String.class, null);
        zzgz.zza(bundle2, "trigger_timeout", Long.class, 0L);
        zzgz.zza(bundle2, "timed_out_event_name", String.class, null);
        zzgz.zza(bundle2, "timed_out_event_params", Bundle.class, null);
        zzgz.zza(bundle2, "triggered_event_name", String.class, null);
        zzgz.zza(bundle2, "triggered_event_params", Bundle.class, null);
        zzgz.zza(bundle2, "time_to_live", Long.class, 0L);
        zzgz.zza(bundle2, "expired_event_name", String.class, null);
        zzgz.zza(bundle2, "expired_event_params", Bundle.class, null);
        Preconditions.checkNotEmpty(bundle2.getString("name"));
        Preconditions.checkNotEmpty(bundle2.getString("origin"));
        Preconditions.checkNotNull(bundle2.get("value"));
        bundle2.putLong("creation_timestamp", j);
        String string = bundle2.getString("name");
        Object obj = bundle2.get("value");
        if (this.zzt.zzv().zzl(string) != 0) {
            this.zzt.zzaA().zzd().zzb("Invalid conditional user property name", this.zzt.zzj().zzf(string));
            return;
        }
        if (this.zzt.zzv().zzd(string, obj) != 0) {
            this.zzt.zzaA().zzd().zzc("Invalid conditional user property value", this.zzt.zzj().zzf(string), obj);
            return;
        }
        Object objZzB = this.zzt.zzv().zzB(string, obj);
        if (objZzB == null) {
            this.zzt.zzaA().zzd().zzc("Unable to normalize conditional user property value", this.zzt.zzj().zzf(string), obj);
            return;
        }
        zzgz.zzb(bundle2, objZzB);
        long j2 = bundle2.getLong("trigger_timeout");
        if (!TextUtils.isEmpty(bundle2.getString("trigger_event_name"))) {
            this.zzt.zzf();
            if (j2 > 15552000000L || j2 < 1) {
                this.zzt.zzaA().zzd().zzc("Invalid conditional user property timeout", this.zzt.zzj().zzf(string), Long.valueOf(j2));
                return;
            }
        }
        long j3 = bundle2.getLong("time_to_live");
        this.zzt.zzf();
        if (j3 > 15552000000L || j3 < 1) {
            this.zzt.zzaA().zzd().zzc("Invalid conditional user property time to live", this.zzt.zzj().zzf(string), Long.valueOf(j3));
        } else {
            this.zzt.zzaB().zzp(new zzht(this, bundle2));
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:48:0x00c0
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public final void zzR(com.google.android.gms.measurement.internal.zzhb r13, long r14) {
        /*
            r12 = this;
            r12.zza()
            int r0 = r13.zza()
            r1 = -10
            if (r0 == r1) goto L28
            java.lang.Boolean r2 = r13.zzf()
            if (r2 != 0) goto L28
            java.lang.Boolean r2 = r13.zzg()
            if (r2 == 0) goto L18
            goto L28
        L18:
            com.google.android.gms.measurement.internal.zzgd r13 = r12.zzt
            com.google.android.gms.measurement.internal.zzet r13 = r13.zzaA()
            com.google.android.gms.measurement.internal.zzer r13 = r13.zzl()
            java.lang.String r14 = "Discarding empty consent settings"
            r13.zza(r14)
            return
        L28:
            java.lang.Object r2 = r12.zzh
            monitor-enter(r2)
            com.google.android.gms.measurement.internal.zzhb r9 = r12.zzi     // Catch: java.lang.Throwable -> Lbb
            int r3 = r9.zza()     // Catch: java.lang.Throwable -> Lbb
            boolean r3 = com.google.android.gms.measurement.internal.zzhb.zzk(r0, r3)     // Catch: java.lang.Throwable -> Lbb
            r4 = 0
            if (r3 == 0) goto L62
            com.google.android.gms.measurement.internal.zzhb r3 = r12.zzi     // Catch: java.lang.Throwable -> L51
            boolean r3 = r13.zzm(r3)     // Catch: java.lang.Throwable -> L51
            com.google.android.gms.measurement.internal.zzha r5 = com.google.android.gms.measurement.internal.zzha.ANALYTICS_STORAGE     // Catch: java.lang.Throwable -> L51
            boolean r6 = r13.zzj(r5)     // Catch: java.lang.Throwable -> L51
            r7 = 1
            if (r6 == 0) goto L56
            com.google.android.gms.measurement.internal.zzhb r6 = r12.zzi     // Catch: java.lang.Throwable -> L51
            boolean r5 = r6.zzj(r5)     // Catch: java.lang.Throwable -> L51
            if (r5 != 0) goto L56
            r4 = 1
            goto L56
        L51:
            r0 = move-exception
            r13 = r0
            r4 = r12
            goto Lbe
        L56:
            com.google.android.gms.measurement.internal.zzhb r5 = r12.zzi     // Catch: java.lang.Throwable -> L51
            com.google.android.gms.measurement.internal.zzhb r13 = r13.zze(r5)     // Catch: java.lang.Throwable -> L51
            r12.zzi = r13     // Catch: java.lang.Throwable -> L51
            r8 = r4
            r4 = 1
        L60:
            r5 = r13
            goto L65
        L62:
            r3 = 0
            r8 = 0
            goto L60
        L65:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> Lbb
            if (r4 != 0) goto L78
            com.google.android.gms.measurement.internal.zzgd r13 = r12.zzt
            com.google.android.gms.measurement.internal.zzet r13 = r13.zzaA()
            com.google.android.gms.measurement.internal.zzer r13 = r13.zzi()
            java.lang.String r14 = "Ignoring lower-priority consent settings, proposed settings"
            r13.zzb(r14, r5)
            return
        L78:
            java.util.concurrent.atomic.AtomicLong r13 = r12.zzj
            long r6 = r13.getAndIncrement()
            if (r3 == 0) goto L9a
            java.util.concurrent.atomic.AtomicReference r13 = r12.zzg
            r0 = 0
            r13.set(r0)
            com.google.android.gms.measurement.internal.zzgd r13 = r12.zzt
            com.google.android.gms.measurement.internal.zzga r13 = r13.zzaB()
            com.google.android.gms.measurement.internal.zzif r3 = new com.google.android.gms.measurement.internal.zzif
            r4 = r12
            r10 = r8
            r11 = r9
            r8 = r6
            r6 = r14
            r3.<init>(r4, r5, r6, r8, r10, r11)
            r13.zzq(r3)
            return
        L9a:
            com.google.android.gms.measurement.internal.zzig r3 = new com.google.android.gms.measurement.internal.zzig
            r4 = r12
            r3.<init>(r4, r5, r6, r8, r9)
            r13 = 30
            if (r0 == r13) goto Lb1
            if (r0 != r1) goto La7
            goto Lb1
        La7:
            com.google.android.gms.measurement.internal.zzgd r13 = r4.zzt
            com.google.android.gms.measurement.internal.zzga r13 = r13.zzaB()
            r13.zzp(r3)
            return
        Lb1:
            com.google.android.gms.measurement.internal.zzgd r13 = r4.zzt
            com.google.android.gms.measurement.internal.zzga r13 = r13.zzaB()
            r13.zzq(r3)
            return
        Lbb:
            r0 = move-exception
            r4 = r12
        Lbd:
            r13 = r0
        Lbe:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> Lc0
            throw r13
        Lc0:
            r0 = move-exception
            goto Lbd
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzik.zzR(com.google.android.gms.measurement.internal.zzhb, long):void");
    }

    public final void zzS(Bundle bundle, int i, long j) {
        zza();
        String strZzh = zzhb.zzh(bundle);
        if (strZzh != null) {
            this.zzt.zzaA().zzl().zzb("Ignoring invalid consent setting", strZzh);
            this.zzt.zzaA().zzl().zza("Valid consent values are 'granted', 'denied'");
        }
        zzR(zzhb.zzb(bundle, i), j);
    }

    public final void zzT(zzhf zzhfVar) {
        zzhf zzhfVar2;
        zzg();
        zza();
        if (zzhfVar != null && zzhfVar != (zzhfVar2 = this.zzd)) {
            Preconditions.checkState(zzhfVar2 == null, "EventInterceptor already set.");
        }
        this.zzd = zzhfVar;
    }

    public final void zzU(Boolean bool) {
        zza();
        this.zzt.zzaB().zzp(new zzie(this, bool));
    }

    final void zzV(zzhb zzhbVar) {
        zzg();
        boolean z = (zzhbVar.zzj(zzha.ANALYTICS_STORAGE) && zzhbVar.zzj(zzha.AD_STORAGE)) || this.zzt.zzt().zzM();
        if (z != this.zzt.zzK()) {
            this.zzt.zzG(z);
            zzfi zzfiVarZzm = this.zzt.zzm();
            zzgd zzgdVar = zzfiVarZzm.zzt;
            zzfiVarZzm.zzg();
            Boolean boolValueOf = zzfiVarZzm.zza().contains("measurement_enabled_from_api") ? Boolean.valueOf(zzfiVarZzm.zza().getBoolean("measurement_enabled_from_api", true)) : null;
            if (!z || boolValueOf == null || boolValueOf.booleanValue()) {
                zzaa(Boolean.valueOf(z), false);
            }
        }
    }

    public final void zzW(String str, String str2, Object obj, boolean z) {
        zzX("auto", "_ldl", obj, true, this.zzt.zzax().currentTimeMillis());
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final void zzY(java.lang.String r9, java.lang.String r10, java.lang.Object r11, long r12) {
        /*
            r8 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r9)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r10)
            r8.zzg()
            r8.zza()
            java.lang.String r0 = "allow_personalized_ads"
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L62
            boolean r0 = r11 instanceof java.lang.String
            java.lang.String r1 = "_npa"
            if (r0 == 0) goto L50
            r0 = r11
            java.lang.String r0 = (java.lang.String) r0
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L50
            java.util.Locale r10 = java.util.Locale.ENGLISH
            java.lang.String r10 = r0.toLowerCase(r10)
            r11 = 1
            java.lang.String r0 = "false"
            boolean r10 = r0.equals(r10)
            r2 = 1
            if (r11 == r10) goto L37
            r10 = 0
            goto L38
        L37:
            r10 = r2
        L38:
            java.lang.Long r4 = java.lang.Long.valueOf(r10)
            com.google.android.gms.measurement.internal.zzgd r5 = r8.zzt
            com.google.android.gms.measurement.internal.zzfi r5 = r5.zzm()
            com.google.android.gms.measurement.internal.zzfh r5 = r5.zzh
            int r6 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r6 != 0) goto L4a
            java.lang.String r0 = "true"
        L4a:
            r5.zzb(r0)
            r3 = r1
            r6 = r4
            goto L64
        L50:
            if (r11 != 0) goto L62
            com.google.android.gms.measurement.internal.zzgd r10 = r8.zzt
            com.google.android.gms.measurement.internal.zzfi r10 = r10.zzm()
            com.google.android.gms.measurement.internal.zzfh r10 = r10.zzh
            java.lang.String r0 = "unset"
            r10.zzb(r0)
            r6 = r11
            r3 = r1
            goto L64
        L62:
            r3 = r10
            r6 = r11
        L64:
            com.google.android.gms.measurement.internal.zzgd r10 = r8.zzt
            boolean r10 = r10.zzJ()
            if (r10 != 0) goto L7c
            com.google.android.gms.measurement.internal.zzgd r9 = r8.zzt
            com.google.android.gms.measurement.internal.zzet r9 = r9.zzaA()
            com.google.android.gms.measurement.internal.zzer r9 = r9.zzj()
            java.lang.String r10 = "User property not set since app measurement is disabled"
            r9.zza(r10)
            return
        L7c:
            com.google.android.gms.measurement.internal.zzgd r10 = r8.zzt
            boolean r10 = r10.zzM()
            if (r10 != 0) goto L85
            return
        L85:
            com.google.android.gms.measurement.internal.zzlk r2 = new com.google.android.gms.measurement.internal.zzlk
            r7 = r9
            r4 = r12
            r2.<init>(r3, r4, r6, r7)
            com.google.android.gms.measurement.internal.zzgd r9 = r8.zzt
            com.google.android.gms.measurement.internal.zzjz r9 = r9.zzt()
            r9.zzK(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzik.zzY(java.lang.String, java.lang.String, java.lang.Object, long):void");
    }

    public final void zzZ(zzhg zzhgVar) {
        zza();
        Preconditions.checkNotNull(zzhgVar);
        if (this.zze.remove(zzhgVar)) {
            return;
        }
        this.zzt.zzaA().zzk().zza("OnEventListener had not been registered");
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    protected final boolean zzf() {
        return false;
    }

    public final int zzh(String str) {
        Preconditions.checkNotEmpty(str);
        this.zzt.zzf();
        return 25;
    }

    public final Boolean zzi() {
        AtomicReference atomicReference = new AtomicReference();
        return (Boolean) this.zzt.zzaB().zzd(atomicReference, 15000L, "boolean test flag value", new zzhw(this, atomicReference));
    }

    public final Double zzj() {
        AtomicReference atomicReference = new AtomicReference();
        return (Double) this.zzt.zzaB().zzd(atomicReference, 15000L, "double test flag value", new zzid(this, atomicReference));
    }

    public final Integer zzl() {
        AtomicReference atomicReference = new AtomicReference();
        return (Integer) this.zzt.zzaB().zzd(atomicReference, 15000L, "int test flag value", new zzic(this, atomicReference));
    }

    public final Long zzm() {
        AtomicReference atomicReference = new AtomicReference();
        return (Long) this.zzt.zzaB().zzd(atomicReference, 15000L, "long test flag value", new zzib(this, atomicReference));
    }

    public final String zzo() {
        return (String) this.zzg.get();
    }

    public final String zzp() {
        zzir zzirVarZzi = this.zzt.zzs().zzi();
        if (zzirVarZzi != null) {
            return zzirVarZzi.zzb;
        }
        return null;
    }

    public final String zzq() {
        zzir zzirVarZzi = this.zzt.zzs().zzi();
        if (zzirVarZzi != null) {
            return zzirVarZzi.zza;
        }
        return null;
    }

    public final String zzr() {
        AtomicReference atomicReference = new AtomicReference();
        return (String) this.zzt.zzaB().zzd(atomicReference, 15000L, "String test flag value", new zzia(this, atomicReference));
    }

    public final ArrayList zzs(String str, String str2) {
        if (this.zzt.zzaB().zzs()) {
            this.zzt.zzaA().zzd().zza("Cannot get conditional user properties from analytics worker thread");
            return new ArrayList(0);
        }
        this.zzt.zzay();
        if (zzab.zza()) {
            this.zzt.zzaA().zzd().zza("Cannot get conditional user properties from main thread");
            return new ArrayList(0);
        }
        AtomicReference atomicReference = new AtomicReference();
        this.zzt.zzaB().zzd(atomicReference, 5000L, "get conditional user properties", new zzhv(this, atomicReference, null, str, str2));
        List list = (List) atomicReference.get();
        if (list != null) {
            return zzlp.zzH(list);
        }
        this.zzt.zzaA().zzd().zzb("Timed out waiting for get conditional user properties", null);
        return new ArrayList();
    }

    public final Map zzu(String str, String str2, boolean z) {
        if (this.zzt.zzaB().zzs()) {
            this.zzt.zzaA().zzd().zza("Cannot get user properties from analytics worker thread");
            return Collections.EMPTY_MAP;
        }
        this.zzt.zzay();
        if (zzab.zza()) {
            this.zzt.zzaA().zzd().zza("Cannot get user properties from main thread");
            return Collections.EMPTY_MAP;
        }
        AtomicReference atomicReference = new AtomicReference();
        this.zzt.zzaB().zzd(atomicReference, 5000L, "get user properties", new zzhx(this, atomicReference, null, str, str2, z));
        List<zzlk> list = (List) atomicReference.get();
        if (list == null) {
            this.zzt.zzaA().zzd().zzb("Timed out waiting for handle get user properties, includeInternal", Boolean.valueOf(z));
            return Collections.EMPTY_MAP;
        }
        ArrayMap arrayMap = new ArrayMap(list.size());
        for (zzlk zzlkVar : list) {
            Object objZza = zzlkVar.zza();
            if (objZza != null) {
                arrayMap.put(zzlkVar.zzb, objZza);
            }
        }
        return arrayMap;
    }

    public final void zzz() {
        zzg();
        zza();
        if (this.zzt.zzM()) {
            if (this.zzt.zzf().zzs(null, zzeg.zzZ)) {
                zzag zzagVarZzf = this.zzt.zzf();
                zzagVarZzf.zzt.zzay();
                Boolean boolZzk = zzagVarZzf.zzk("google_analytics_deferred_deep_link_enabled");
                if (boolZzk != null && boolZzk.booleanValue()) {
                    this.zzt.zzaA().zzc().zza("Deferred Deep Link feature enabled.");
                    this.zzt.zzaB().zzp(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzhm
                        @Override // java.lang.Runnable
                        public final void run() {
                            zzik zzikVar = this.zza;
                            zzikVar.zzg();
                            if (zzikVar.zzt.zzm().zzn.zzb()) {
                                zzikVar.zzt.zzaA().zzc().zza("Deferred Deep Link already retrieved. Not fetching again.");
                                return;
                            }
                            long jZza = zzikVar.zzt.zzm().zzo.zza();
                            zzikVar.zzt.zzm().zzo.zzb(1 + jZza);
                            zzikVar.zzt.zzf();
                            if (jZza < 5) {
                                zzikVar.zzt.zzE();
                            } else {
                                zzikVar.zzt.zzaA().zzk().zza("Permanently failed to retrieve Deferred Deep Link. Reached maximum retries.");
                                zzikVar.zzt.zzm().zzn.zza(true);
                            }
                        }
                    });
                }
            }
            this.zzt.zzt().zzq();
            this.zzc = false;
            zzfi zzfiVarZzm = this.zzt.zzm();
            zzfiVarZzm.zzg();
            String string = zzfiVarZzm.zza().getString("previous_os_version", null);
            zzfiVarZzm.zzt.zzg().zzv();
            String str = Build.VERSION.RELEASE;
            if (!TextUtils.isEmpty(str) && !str.equals(string)) {
                SharedPreferences.Editor editorEdit = zzfiVarZzm.zza().edit();
                editorEdit.putString("previous_os_version", str);
                editorEdit.apply();
            }
            if (TextUtils.isEmpty(string)) {
                return;
            }
            this.zzt.zzg().zzv();
            if (string.equals(str)) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("_po", string);
            zzG("auto", "_ou", bundle);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zzX(java.lang.String r17, java.lang.String r18, java.lang.Object r19, boolean r20, long r21) {
        /*
            r16 = this;
            r0 = r16
            r2 = r18
            r1 = r19
            r3 = 0
            r4 = 24
            if (r20 == 0) goto L17
            com.google.android.gms.measurement.internal.zzgd r5 = r0.zzt
            com.google.android.gms.measurement.internal.zzlp r5 = r5.zzv()
            int r5 = r5.zzl(r2)
            r12 = r5
            goto L43
        L17:
            com.google.android.gms.measurement.internal.zzgd r5 = r0.zzt
            com.google.android.gms.measurement.internal.zzlp r5 = r5.zzv()
            java.lang.String r6 = "user property"
            boolean r7 = r5.zzad(r6, r2)
            r8 = 6
            if (r7 != 0) goto L28
        L26:
            r12 = 6
            goto L43
        L28:
            java.lang.String[] r7 = com.google.android.gms.measurement.internal.zzhe.zza
            r9 = 0
            boolean r7 = r5.zzaa(r6, r7, r9, r2)
            if (r7 != 0) goto L36
            r5 = 15
            r12 = 15
            goto L43
        L36:
            com.google.android.gms.measurement.internal.zzgd r7 = r5.zzt
            r7.zzf()
            boolean r5 = r5.zzZ(r6, r4, r2)
            if (r5 != 0) goto L42
            goto L26
        L42:
            r12 = 0
        L43:
            r5 = 1
            if (r12 == 0) goto L6d
            com.google.android.gms.measurement.internal.zzgd r1 = r0.zzt
            com.google.android.gms.measurement.internal.zzlp r1 = r1.zzv()
            com.google.android.gms.measurement.internal.zzgd r6 = r0.zzt
            r6.zzf()
            java.lang.String r14 = r1.zzD(r2, r4, r5)
            if (r2 == 0) goto L5d
            int r3 = r2.length()
            r15 = r3
            goto L5e
        L5d:
            r15 = 0
        L5e:
            com.google.android.gms.measurement.internal.zzgd r1 = r0.zzt
            com.google.android.gms.measurement.internal.zzlp r9 = r1.zzv()
            com.google.android.gms.measurement.internal.zzlo r10 = r0.zzl
            r11 = 0
            java.lang.String r13 = "_ev"
            r9.zzO(r10, r11, r12, r13, r14, r15)
            return
        L6d:
            if (r17 != 0) goto L72
            java.lang.String r6 = "app"
            goto L74
        L72:
            r6 = r17
        L74:
            if (r1 == 0) goto Lc7
            com.google.android.gms.measurement.internal.zzgd r7 = r0.zzt
            com.google.android.gms.measurement.internal.zzlp r7 = r7.zzv()
            int r11 = r7.zzd(r2, r1)
            if (r11 == 0) goto Lb4
            com.google.android.gms.measurement.internal.zzgd r6 = r0.zzt
            com.google.android.gms.measurement.internal.zzlp r6 = r6.zzv()
            com.google.android.gms.measurement.internal.zzgd r7 = r0.zzt
            r7.zzf()
            java.lang.String r13 = r6.zzD(r2, r4, r5)
            boolean r2 = r1 instanceof java.lang.String
            if (r2 != 0) goto L9c
            boolean r2 = r1 instanceof java.lang.CharSequence
            if (r2 == 0) goto L9a
            goto L9c
        L9a:
            r14 = 0
            goto La5
        L9c:
            java.lang.String r1 = r1.toString()
            int r3 = r1.length()
            r14 = r3
        La5:
            com.google.android.gms.measurement.internal.zzgd r1 = r0.zzt
            com.google.android.gms.measurement.internal.zzlp r8 = r1.zzv()
            com.google.android.gms.measurement.internal.zzlo r9 = r0.zzl
            r10 = 0
            java.lang.String r12 = "_ev"
            r8.zzO(r9, r10, r11, r12, r13, r14)
            return
        Lb4:
            com.google.android.gms.measurement.internal.zzgd r3 = r0.zzt
            com.google.android.gms.measurement.internal.zzlp r3 = r3.zzv()
            java.lang.Object r5 = r3.zzB(r2, r1)
            if (r5 == 0) goto Lc6
            r3 = r21
            r1 = r6
            r0.zzN(r1, r2, r3, r5)
        Lc6:
            return
        Lc7:
            r1 = r6
            r5 = 0
            r0 = r16
            r2 = r18
            r3 = r21
            r0.zzN(r1, r2, r3, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzik.zzX(java.lang.String, java.lang.String, java.lang.Object, boolean, long):void");
    }
}
