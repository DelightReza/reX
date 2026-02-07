package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
final class zzma implements zzmu {
    private static final zzmg zza = new zzly();
    private final zzmg zzb;

    public zzma() {
        zzmg zzmgVar;
        zzkw zzkwVarZza = zzkw.zza();
        try {
            zzmgVar = (zzmg) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", null).invoke(null, null);
        } catch (Exception unused) {
            zzmgVar = zza;
        }
        zzlz zzlzVar = new zzlz(zzkwVarZza, zzmgVar);
        byte[] bArr = zzlj.zzd;
        this.zzb = zzlzVar;
    }

    private static boolean zzb(zzmf zzmfVar) {
        return zzmfVar.zzc() == 1;
    }

    @Override // com.google.android.gms.internal.measurement.zzmu
    public final zzmt zza(Class cls) {
        zzmv.zzC(cls);
        zzmf zzmfVarZzb = this.zzb.zzb(cls);
        return zzmfVarZzb.zzb() ? zzlb.class.isAssignableFrom(cls) ? zzmm.zzc(zzmv.zzz(), zzkq.zzb(), zzmfVarZzb.zza()) : zzmm.zzc(zzmv.zzy(), zzkq.zza(), zzmfVarZzb.zza()) : zzlb.class.isAssignableFrom(cls) ? zzb(zzmfVarZzb) ? zzml.zzl(cls, zzmfVarZzb, zzmo.zzb(), zzlw.zzd(), zzmv.zzz(), zzkq.zzb(), zzme.zzb()) : zzml.zzl(cls, zzmfVarZzb, zzmo.zzb(), zzlw.zzd(), zzmv.zzz(), null, zzme.zzb()) : zzb(zzmfVarZzb) ? zzml.zzl(cls, zzmfVarZzb, zzmo.zza(), zzlw.zzc(), zzmv.zzy(), zzkq.zza(), zzme.zza()) : zzml.zzl(cls, zzmfVarZzb, zzmo.zza(), zzlw.zzc(), zzmv.zzy(), null, zzme.zza());
    }
}
