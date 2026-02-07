package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class zzaz extends zzaw {
    protected zzaz() {
        this.zza.add(zzbl.APPLY);
        this.zza.add(zzbl.BLOCK);
        this.zza.add(zzbl.BREAK);
        this.zza.add(zzbl.CASE);
        this.zza.add(zzbl.DEFAULT);
        this.zza.add(zzbl.CONTINUE);
        this.zza.add(zzbl.DEFINE_FUNCTION);
        this.zza.add(zzbl.FN);
        this.zza.add(zzbl.IF);
        this.zza.add(zzbl.QUOTE);
        this.zza.add(zzbl.RETURN);
        this.zza.add(zzbl.SWITCH);
        this.zza.add(zzbl.TERNARY);
    }

    private static zzap zzc(zzg zzgVar, List list) {
        zzh.zzi(zzbl.FN.name(), 2, list);
        zzap zzapVarZzb = zzgVar.zzb((zzap) list.get(0));
        zzap zzapVarZzb2 = zzgVar.zzb((zzap) list.get(1));
        if (!(zzapVarZzb2 instanceof zzae)) {
            throw new IllegalArgumentException(String.format("FN requires an ArrayValue of parameter names found %s", zzapVarZzb2.getClass().getCanonicalName()));
        }
        List listZzm = ((zzae) zzapVarZzb2).zzm();
        List arrayList = new ArrayList();
        if (list.size() > 2) {
            arrayList = list.subList(2, list.size());
        }
        return new zzao(zzapVarZzb.zzi(), listZzm, arrayList, zzgVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzaw
    public final zzap zza(String str, zzg zzgVar, List list) {
        zzbl zzblVar = zzbl.ADD;
        int iOrdinal = zzh.zze(str).ordinal();
        if (iOrdinal == 2) {
            zzh.zzh(zzbl.APPLY.name(), 3, list);
            zzap zzapVarZzb = zzgVar.zzb((zzap) list.get(0));
            String strZzi = zzgVar.zzb((zzap) list.get(1)).zzi();
            zzap zzapVarZzb2 = zzgVar.zzb((zzap) list.get(2));
            if (!(zzapVarZzb2 instanceof zzae)) {
                throw new IllegalArgumentException(String.format("Function arguments for Apply are not a list found %s", zzapVarZzb2.getClass().getCanonicalName()));
            }
            if (strZzi.isEmpty()) {
                throw new IllegalArgumentException("Function name for apply is undefined");
            }
            return zzapVarZzb.zzbU(strZzi, zzgVar, ((zzae) zzapVarZzb2).zzm());
        }
        if (iOrdinal == 15) {
            zzh.zzh(zzbl.BREAK.name(), 0, list);
            return zzap.zzh;
        }
        if (iOrdinal == 25) {
            return zzc(zzgVar, list);
        }
        if (iOrdinal == 41) {
            zzh.zzi(zzbl.IF.name(), 2, list);
            zzap zzapVarZzb3 = zzgVar.zzb((zzap) list.get(0));
            zzap zzapVarZzb4 = zzgVar.zzb((zzap) list.get(1));
            zzap zzapVarZzb5 = list.size() > 2 ? zzgVar.zzb((zzap) list.get(2)) : null;
            zzap zzapVar = zzap.zzf;
            zzap zzapVarZzc = zzapVarZzb3.zzg().booleanValue() ? zzgVar.zzc((zzae) zzapVarZzb4) : zzapVarZzb5 != null ? zzgVar.zzc((zzae) zzapVarZzb5) : zzapVar;
            return zzapVarZzc instanceof zzag ? zzapVarZzc : zzapVar;
        }
        if (iOrdinal == 54) {
            return new zzae(list);
        }
        if (iOrdinal == 57) {
            if (list.isEmpty()) {
                return zzap.zzj;
            }
            zzh.zzh(zzbl.RETURN.name(), 1, list);
            return new zzag("return", zzgVar.zzb((zzap) list.get(0)));
        }
        if (iOrdinal != 19) {
            if (iOrdinal == 20) {
                zzh.zzi(zzbl.DEFINE_FUNCTION.name(), 2, list);
                zzap zzapVarZzc2 = zzc(zzgVar, list);
                zzai zzaiVar = (zzai) zzapVarZzc2;
                if (zzaiVar.zzc() == null) {
                    zzgVar.zzg("", zzapVarZzc2);
                    return zzapVarZzc2;
                }
                zzgVar.zzg(zzaiVar.zzc(), zzapVarZzc2);
                return zzapVarZzc2;
            }
            if (iOrdinal == 60) {
                zzh.zzh(zzbl.SWITCH.name(), 3, list);
                zzap zzapVarZzb6 = zzgVar.zzb((zzap) list.get(0));
                zzap zzapVarZzb7 = zzgVar.zzb((zzap) list.get(1));
                zzap zzapVarZzb8 = zzgVar.zzb((zzap) list.get(2));
                if (!(zzapVarZzb7 instanceof zzae)) {
                    throw new IllegalArgumentException("Malformed SWITCH statement, cases are not a list");
                }
                if (!(zzapVarZzb8 instanceof zzae)) {
                    throw new IllegalArgumentException("Malformed SWITCH statement, case statements are not a list");
                }
                zzae zzaeVar = (zzae) zzapVarZzb7;
                zzae zzaeVar2 = (zzae) zzapVarZzb8;
                boolean z = false;
                for (int i = 0; i < zzaeVar.zzc(); i++) {
                    if (z || zzapVarZzb6.equals(zzgVar.zzb(zzaeVar.zze(i)))) {
                        zzap zzapVarZzb9 = zzgVar.zzb(zzaeVar2.zze(i));
                        if (zzapVarZzb9 instanceof zzag) {
                            return ((zzag) zzapVarZzb9).zzc().equals("break") ? zzap.zzf : zzapVarZzb9;
                        }
                        z = true;
                    } else {
                        z = false;
                    }
                }
                if (zzaeVar.zzc() + 1 == zzaeVar2.zzc()) {
                    zzap zzapVarZzb10 = zzgVar.zzb(zzaeVar2.zze(zzaeVar.zzc()));
                    if (zzapVarZzb10 instanceof zzag) {
                        String strZzc = ((zzag) zzapVarZzb10).zzc();
                        if (strZzc.equals("return") || strZzc.equals("continue")) {
                            return zzapVarZzb10;
                        }
                    }
                }
                return zzap.zzf;
            }
            if (iOrdinal == 61) {
                zzh.zzh(zzbl.TERNARY.name(), 3, list);
                return zzgVar.zzb((zzap) list.get(0)).zzg().booleanValue() ? zzgVar.zzb((zzap) list.get(1)) : zzgVar.zzb((zzap) list.get(2));
            }
            switch (iOrdinal) {
                case 11:
                    return zzgVar.zza().zzc(new zzae(list));
                case 12:
                    zzh.zzh(zzbl.BREAK.name(), 0, list);
                    return zzap.zzi;
                case 13:
                    break;
                default:
                    return super.zzb(str);
            }
        }
        if (list.isEmpty()) {
            return zzap.zzf;
        }
        zzap zzapVarZzb11 = zzgVar.zzb((zzap) list.get(0));
        return zzapVarZzb11 instanceof zzae ? zzgVar.zzc((zzae) zzapVarZzb11) : zzap.zzf;
    }
}
