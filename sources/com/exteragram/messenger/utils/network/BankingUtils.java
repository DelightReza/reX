package com.exteragram.messenger.utils.network;

import com.android.tools.p002r8.RecordTag;
import com.exteragram.messenger.p003ai.p004ui.AbstractC0746x1d8a54ff;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import p017j$.util.Objects;

/* loaded from: classes.dex */
public abstract class BankingUtils {
    public static List getDonates() {
        Set stringSetConfigValue = RemoteUtils.getStringSetConfigValue("donates", Collections.EMPTY_SET);
        ArrayList arrayList = new ArrayList();
        Iterator it = stringSetConfigValue.iterator();
        while (it.hasNext()) {
            String[] strArrSplit = ((String) it.next()).split("#");
            if (strArrSplit.length == 2) {
                arrayList.add(new Donate(strArrSplit[0], strArrSplit[1]));
            }
        }
        return arrayList;
    }

    public static List getAyuDonates() {
        Set stringSetConfigValue = RemoteUtils.getStringSetConfigValue("donates_ayu", Collections.EMPTY_SET);
        ArrayList arrayList = new ArrayList();
        Iterator it = stringSetConfigValue.iterator();
        while (it.hasNext()) {
            String[] strArrSplit = ((String) it.next()).split("#");
            if (strArrSplit.length == 2) {
                arrayList.add(new Donate(strArrSplit[0], strArrSplit[1]));
            }
        }
        return arrayList;
    }

    public static final class Donate extends RecordTag {
        private final String details;
        private final String name;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof Donate)) {
                return false;
            }
            Donate donate = (Donate) obj;
            return Objects.equals(this.name, donate.name) && Objects.equals(this.details, donate.details);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{this.name, this.details};
        }

        public Donate(String str, String str2) {
            this.name = str;
            this.details = str2;
        }

        public String details() {
            return this.details;
        }

        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        public final int hashCode() {
            return BankingUtils$Donate$$ExternalSyntheticRecord0.m237m(this.name, this.details);
        }

        public String name() {
            return this.name;
        }

        public final String toString() {
            return AbstractC0746x1d8a54ff.m185m($record$getFieldsAsObjects(), Donate.class, "name;details");
        }
    }

    public static final class ExchangeRates extends RecordTag {
        public static final DecimalFormat formatter;
        private final Map rates;

        private /* synthetic */ boolean $record$equals(Object obj) {
            return (obj instanceof ExchangeRates) && Objects.equals(this.rates, ((ExchangeRates) obj).rates);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{this.rates};
        }

        public ExchangeRates(Map map) {
            this.rates = map;
        }

        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        public final int hashCode() {
            return Objects.hashCode(this.rates);
        }

        public Map rates() {
            return this.rates;
        }

        public final String toString() {
            return AbstractC0746x1d8a54ff.m185m($record$getFieldsAsObjects(), ExchangeRates.class, "rates");
        }

        static {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            formatter = decimalFormat;
            decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        }

        public double format(String str) {
            Double d = (Double) rates().get(str);
            if (d == null) {
                return 0.0d;
            }
            double dDoubleValue = d.doubleValue() * RemoteUtils.getFloatConfigValue("donates_amount_usd", 5.0f).floatValue();
            if ("ton".equalsIgnoreCase(str)) {
                dDoubleValue += (RemoteUtils.getIntConfigValue("donates_ton_markup_percent", 10).intValue() / 100.0d) * dDoubleValue;
            }
            return Double.parseDouble(formatter.format(dDoubleValue));
        }
    }
}
