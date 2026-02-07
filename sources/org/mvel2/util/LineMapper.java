package org.mvel2.util;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes4.dex */
public class LineMapper {
    private char[] expr;
    private ArrayList<Node> lineMapping;
    private Set<Integer> lines;

    public interface LineLookup {
        int getLineFromCursor(int i);

        boolean hasLine(int i);
    }

    public LineMapper(char[] cArr) {
        this.expr = cArr;
    }

    public LineLookup map() {
        this.lineMapping = new ArrayList<>();
        this.lines = new TreeSet();
        int i = 0;
        int i2 = 0;
        int i3 = 1;
        while (true) {
            char[] cArr = this.expr;
            if (i >= cArr.length) {
                break;
            }
            if (cArr[i] == '\n') {
                this.lines.add(Integer.valueOf(i3));
                this.lineMapping.add(new Node(i2, i, i3));
                i2 = i + 1;
                i3++;
            }
            i++;
        }
        if (i > i2) {
            this.lines.add(Integer.valueOf(i3));
            this.lineMapping.add(new Node(i2, i, i3));
        }
        return new LineLookup() { // from class: org.mvel2.util.LineMapper.1
            @Override // org.mvel2.util.LineMapper.LineLookup
            public int getLineFromCursor(int i4) {
                ArrayList arrayList = LineMapper.this.lineMapping;
                int size = arrayList.size();
                int i5 = 0;
                while (i5 < size) {
                    Object obj = arrayList.get(i5);
                    i5++;
                    Node node = (Node) obj;
                    if (node.isInRange(i4)) {
                        return node.getLine();
                    }
                }
                return -1;
            }

            @Override // org.mvel2.util.LineMapper.LineLookup
            public boolean hasLine(int i4) {
                return LineMapper.this.lines.contains(Integer.valueOf(i4));
            }
        };
    }

    private static class Node implements Comparable<Node> {
        private int cursorEnd;
        private int cursorStart;
        private int line;

        private Node(int i, int i2, int i3) {
            this.cursorStart = i;
            this.cursorEnd = i2;
            this.line = i3;
        }

        public int getLine() {
            return this.line;
        }

        public boolean isInRange(int i) {
            return i >= this.cursorStart && i <= this.cursorEnd;
        }

        @Override // java.lang.Comparable
        public int compareTo(Node node) {
            if (node.cursorStart >= this.cursorEnd) {
                return 1;
            }
            return node.cursorEnd < this.cursorStart ? -1 : 0;
        }
    }
}
