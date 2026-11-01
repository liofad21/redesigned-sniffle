package com.gothiclegion.iconpack;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends Activity {

    private static final int BG = 0xFF0A0000;
    private static final int RED = 0xFFFF2A2A;
    private static final int SILVER = 0xFFC9C9D1;
    private static final int DIM = 0xFF8A5A5A;
    private static final int GREEN = 0xFF4CD964;

    private static class Row {
        String appLabel;
        Drawable appIcon;
        int rule = -1;
        Drawable packIcon;
    }

    private final List<Row> rows = new ArrayList<>();
    private TextView summary;
    private ListView list;
    private BaseAdapter adapter;

    private int dp(int v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);
        root.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        list = new ListView(this);
        list.setDividerHeight(0);
        list.setBackgroundColor(BG);
        list.addHeaderView(buildHeader(), null, false);
        adapter = new RowAdapter();
        list.setAdapter(adapter);
        root.addView(list, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(root);

        scanAsync();
    }

    private View buildHeader() {
        LinearLayout h = new LinearLayout(this);
        h.setOrientation(LinearLayout.VERTICAL);
        h.setPadding(dp(20), dp(28), dp(20), dp(12));

        TextView title = new TextView(this);
        title.setText("Gothic Legion Icons");
        title.setTextColor(RED);
        title.setTextSize(28);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);
        title.setShadowLayer(14, 0, 0, 0x99FF0000);
        title.setGravity(Gravity.CENTER);
        h.addView(title);

        TextView sub = new TextView(this);
        sub.setText("باك أيقونات + ويدجت للتابلت");
        sub.setTextColor(SILVER);
        sub.setTextSize(15);
        sub.setGravity(Gravity.CENTER);
        sub.setPadding(0, dp(4), 0, dp(14));
        h.addView(sub);

        summary = new TextView(this);
        summary.setText("جاري فحص التطبيقات المثبتة...");
        summary.setTextColor(SILVER);
        summary.setTextSize(14);
        summary.setLineSpacing(0, 1.2f);
        summary.setPadding(0, 0, 0, dp(14));
        h.addView(summary);

        h.addView(button("افتح لانشر مايكروسوفت لتطبيق الباك", new View.OnClickListener() {
            @Override public void onClick(View v) { openLauncher(); }
        }));
        h.addView(button("إضافة ويدجت الساعة", pin(ClockWidget.class)));
        h.addView(button("إضافة ويدجت التاريخ", pin(DateWidget.class)));
        h.addView(button("إضافة ويدجت البطارية", pin(BatteryWidget.class)));
        h.addView(button("إعادة فحص التطبيقات", new View.OnClickListener() {
            @Override public void onClick(View v) { scanAsync(); }
        }));

        TextView how = new TextView(this);
        how.setText("طريقة التطبيق في مايكروسوفت لانشر:\n"
                + "الإعدادات ← التخصيص ← حزمة الأيقونات، واختار Gothic Legion Icons.\n"
                + "لو أيقونة ما اتبدلتش لتطبيق معين، اضغط عليه مطولًا في اللانشر واختار تعديل، "
                + "واختار الأيقونة من الباك يدويًا.");
        how.setTextColor(DIM);
        how.setTextSize(13);
        how.setLineSpacing(0, 1.2f);
        how.setPadding(0, dp(14), 0, dp(6));
        h.addView(how);

        TextView sec = new TextView(this);
        sec.setText("التطبيقات المثبتة على التاب:");
        sec.setTextColor(RED);
        sec.setTextSize(16);
        sec.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        sec.setPadding(0, dp(10), 0, 0);
        h.addView(sec);
        return h;
    }

    private View button(String text, View.OnClickListener l) {
        TextView b = new TextView(this);
        b.setText(text);
        b.setTextColor(SILVER);
        b.setTextSize(15);
        b.setGravity(Gravity.CENTER);
        b.setPadding(dp(16), dp(12), dp(16), dp(12));
        GradientDrawable g = new GradientDrawable();
        g.setColor(0xFF1F0000);
        g.setCornerRadius(dp(14));
        g.setStroke(dp(2), 0xFFB8B8C2);
        b.setBackground(g);
        b.setOnClickListener(l);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = dp(8);
        b.setLayoutParams(lp);
        return b;
    }

    private View.OnClickListener pin(final Class<?> cls) {
        return new View.OnClickListener() {
            @Override public void onClick(View v) {
                AppWidgetManager m = getSystemService(AppWidgetManager.class);
                if (Build.VERSION.SDK_INT >= 26 && m != null && m.isRequestPinAppWidgetSupported()) {
                    m.requestPinAppWidget(new ComponentName(MainActivity.this, cls), null, null);
                } else {
                    Toast.makeText(MainActivity.this,
                            "اللانشر مش بيدعم الإضافة المباشرة. أضفها من قائمة الويدجت (Gothic Legion).",
                            Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private void openLauncher() {
        Intent i = getPackageManager().getLaunchIntentForPackage("com.microsoft.launcher");
        if (i != null) {
            startActivity(i);
        } else {
            Toast.makeText(this, "لانشر مايكروسوفت مش مثبت على الجهاز", Toast.LENGTH_LONG).show();
        }
    }

    private void scanAsync() {
        summary.setText("جاري فحص التطبيقات المثبتة...");
        new Thread(new Runnable() {
            @Override public void run() {
                final List<Row> result = new ArrayList<>();
                final Set<Integer> used = new HashSet<>();
                PackageManager pm = getPackageManager();
                Intent q = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> apps = pm.queryIntentActivities(q, 0);
                for (ResolveInfo ri : apps) {
                    if (getPackageName().equals(ri.activityInfo.packageName)) continue;
                    Row r = new Row();
                    r.appLabel = String.valueOf(ri.loadLabel(pm));
                    r.appIcon = ri.loadIcon(pm);
                    r.rule = IconRules.match(r.appLabel);
                    if (r.rule >= 0) {
                        int id = getResources().getIdentifier(
                                IconRules.RULES[r.rule][0], "drawable", getPackageName());
                        if (id != 0) {
                            r.packIcon = getResources().getDrawable(id, getTheme());
                            used.add(r.rule);
                        } else {
                            r.rule = -1;
                        }
                    }
                    result.add(r);
                }
                Collections.sort(result, new Comparator<Row>() {
                    @Override public int compare(Row a, Row b) {
                        boolean ma = a.rule >= 0, mb = b.rule >= 0;
                        if (ma != mb) return ma ? -1 : 1;
                        return a.appLabel.compareToIgnoreCase(b.appLabel);
                    }
                });
                final int matched = countMatched(result);
                final StringBuilder missing = new StringBuilder();
                for (int i = 0; i < IconRules.RULES.length; i++) {
                    if (!used.contains(i)) {
                        if (missing.length() > 0) missing.append("، ");
                        missing.append(IconRules.RULES[i][1]);
                    }
                }
                final int usedCount = used.size();
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        rows.clear();
                        rows.addAll(result);
                        adapter.notifyDataSetChanged();
                        String s = "تم مطابقة " + matched + " تطبيق مع " + usedCount
                                + " أيقونة من " + IconRules.RULES.length + " في الباك.";
                        if (missing.length() > 0) {
                            s += "\nأيقونات مالقتش لها تطبيق على التاب: " + missing;
                        }
                        summary.setText(s);
                    }
                });
            }
        }).start();
    }

    private int countMatched(List<Row> l) {
        int c = 0;
        for (Row r : l) if (r.rule >= 0) c++;
        return c;
    }

    private class RowAdapter extends BaseAdapter {
        @Override public int getCount() { return rows.size(); }
        @Override public Object getItem(int p) { return rows.get(p); }
        @Override public long getItemId(int p) { return p; }

        @Override
        public View getView(int position, View convert, ViewGroup parent) {
            Row r = rows.get(position);
            LinearLayout row = new LinearLayout(MainActivity.this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setPadding(dp(20), dp(8), dp(20), dp(8));

            ImageView appIcon = new ImageView(MainActivity.this);
            appIcon.setImageDrawable(r.appIcon);
            row.addView(appIcon, new LinearLayout.LayoutParams(dp(44), dp(44)));

            if (r.packIcon != null) {
                TextView arrow = new TextView(MainActivity.this);
                arrow.setText("←");
                arrow.setTextColor(RED);
                arrow.setTextSize(20);
                arrow.setPadding(dp(8), 0, dp(8), 0);
                row.addView(arrow);
                ImageView pack = new ImageView(MainActivity.this);
                pack.setImageDrawable(r.packIcon);
                row.addView(pack, new LinearLayout.LayoutParams(dp(44), dp(44)));
            }

            LinearLayout texts = new LinearLayout(MainActivity.this);
            texts.setOrientation(LinearLayout.VERTICAL);
            texts.setPadding(dp(12), 0, dp(12), 0);

            TextView name = new TextView(MainActivity.this);
            name.setText(r.appLabel);
            name.setTextColor(SILVER);
            name.setTextSize(16);
            texts.addView(name);

            TextView status = new TextView(MainActivity.this);
            if (r.rule >= 0) {
                status.setText("✓ أيقونة " + IconRules.RULES[r.rule][1]);
                status.setTextColor(GREEN);
            } else {
                status.setText("مفيش أيقونة مطابقة في الباك");
                status.setTextColor(DIM);
            }
            status.setTextSize(13);
            texts.addView(status);

            row.addView(texts, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            return row;
        }
    }
}
