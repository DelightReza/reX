package p017j$.time;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1746t;
import p017j$.util.C1851q0;
import p017j$.util.C2127w;
import p017j$.util.C2128x;
import p017j$.util.C2130z;
import p017j$.util.Objects;
import p017j$.util.function.BiConsumer$CC;
import p017j$.util.function.BiFunction$CC;
import p017j$.util.function.Function$CC;
import p017j$.util.stream.Collectors;

/* renamed from: j$.time.e */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1678e implements InterfaceC1741o, Function, IntFunction, Supplier, BiConsumer, BinaryOperator {

    /* renamed from: a */
    public final /* synthetic */ int f531a;

    public /* synthetic */ C1678e(int i) {
        this.f531a = i;
    }

    @Override // java.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        switch (this.f531a) {
            case 13:
                ((Collection) obj).add(obj2);
                break;
            case 16:
                ((List) obj).add(obj2);
                break;
            case 19:
                ((Set) obj).add(obj2);
                break;
            case 23:
                ((C1851q0) obj).m912a((CharSequence) obj2);
                break;
            default:
                ((StringBuilder) obj).append((CharSequence) obj2);
                break;
        }
    }

    public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        switch (this.f531a) {
            case 13:
                break;
            case 16:
                break;
            case 19:
                break;
            case 23:
                break;
        }
        return BiConsumer$CC.$default$andThen(this, biConsumer);
    }

    public /* synthetic */ BiFunction andThen(Function function) {
        switch (this.f531a) {
            case 14:
                break;
            case 21:
                break;
            case 24:
                break;
            case 26:
                break;
        }
        return BiFunction$CC.$default$andThen(this, function);
    }

    /* renamed from: andThen, reason: collision with other method in class */
    public /* synthetic */ Function m2949andThen(Function function) {
        switch (this.f531a) {
            case 10:
                break;
            case 22:
                break;
        }
        return Function$CC.$default$andThen(this, function);
    }

    @Override // java.util.function.Function
    public Object apply(Object obj) {
        switch (this.f531a) {
            case 22:
                Set set = Collectors.f978a;
            case 10:
                return obj;
            default:
                return ((C1851q0) obj).toString();
        }
    }

    public /* synthetic */ Function compose(Function function) {
        switch (this.f531a) {
            case 10:
                break;
            case 22:
                break;
        }
        return Function$CC.$default$compose(this, function);
    }

    @Override // java.util.function.Supplier
    public Object get() {
        switch (this.f531a) {
            case 12:
                return new C2127w();
            case 13:
            case 14:
            case 16:
            case 19:
            default:
                return new StringBuilder();
            case 15:
                return new ArrayList();
            case 17:
                return new C2128x();
            case 18:
                return new HashSet();
            case 20:
                return new C2130z();
        }
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        EnumC1727a enumC1727a = EnumC1727a.DAY_OF_MONTH;
        return interfaceC1739m.mo556c(interfaceC1739m.mo545k(enumC1727a).f700d, enumC1727a);
    }

    @Override // java.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        switch (this.f531a) {
            case 14:
                Collection collection = (Collection) obj;
                Set set = Collectors.f978a;
                collection.addAll((Collection) obj2);
                return collection;
            case 21:
                List list = (List) obj;
                Set set2 = Collectors.f978a;
                list.addAll((List) obj2);
                return list;
            case 24:
                C1851q0 c1851q0 = (C1851q0) obj;
                C1851q0 c1851q02 = (C1851q0) obj2;
                c1851q0.getClass();
                Objects.requireNonNull(c1851q02);
                if (c1851q02.f954d != null) {
                    c1851q02.m913b();
                    c1851q0.m912a(c1851q02.f954d[0]);
                }
                return c1851q0;
            case 26:
                Set set3 = (Set) obj;
                Set set4 = (Set) obj2;
                Set set5 = Collectors.f978a;
                if (set3.size() < set4.size()) {
                    set4.addAll(set3);
                    return set4;
                }
                set3.addAll(set4);
                return set3;
            default:
                StringBuilder sb = (StringBuilder) obj;
                Set set6 = Collectors.f978a;
                sb.append((CharSequence) obj2);
                return sb;
        }
    }

    /* renamed from: g */
    public Object m704g(InterfaceC1740n interfaceC1740n) {
        switch (this.f531a) {
            case 0:
                return LocalDate.m561S(interfaceC1740n);
            case 1:
                ZoneId zoneId = (ZoneId) interfaceC1740n.mo547t(AbstractC1745s.f690a);
                if (zoneId == null || (zoneId instanceof ZoneOffset)) {
                    return null;
                }
                return zoneId;
            case 2:
            default:
                EnumC1727a enumC1727a = EnumC1727a.NANO_OF_DAY;
                if (interfaceC1740n.mo543e(enumC1727a)) {
                    return C1715i.m772V(interfaceC1740n.mo542D(enumC1727a));
                }
                return null;
            case 3:
                return (ZoneId) interfaceC1740n.mo547t(AbstractC1745s.f690a);
            case 4:
                return (InterfaceC1661k) interfaceC1740n.mo547t(AbstractC1745s.f691b);
            case 5:
                return (InterfaceC1746t) interfaceC1740n.mo547t(AbstractC1745s.f692c);
            case 6:
                EnumC1727a enumC1727a2 = EnumC1727a.OFFSET_SECONDS;
                if (interfaceC1740n.mo543e(enumC1727a2)) {
                    return ZoneOffset.m626W(interfaceC1740n.mo544i(enumC1727a2));
                }
                return null;
            case 7:
                ZoneId zoneId2 = (ZoneId) interfaceC1740n.mo547t(AbstractC1745s.f690a);
                return zoneId2 != null ? zoneId2 : (ZoneId) interfaceC1740n.mo547t(AbstractC1745s.f693d);
            case 8:
                EnumC1727a enumC1727a3 = EnumC1727a.EPOCH_DAY;
                if (interfaceC1740n.mo543e(enumC1727a3)) {
                    return LocalDate.m563b0(interfaceC1740n.mo542D(enumC1727a3));
                }
                return null;
        }
    }

    public String toString() {
        switch (this.f531a) {
            case 3:
                return "ZoneId";
            case 4:
                return "Chronology";
            case 5:
                return "Precision";
            case 6:
                return "ZoneOffset";
            case 7:
                return "Zone";
            case 8:
                return "LocalDate";
            case 9:
                return "LocalTime";
            default:
                return super.toString();
        }
    }

    @Override // java.util.function.IntFunction
    public Object apply(int i) {
        return new Object[i];
    }
}
