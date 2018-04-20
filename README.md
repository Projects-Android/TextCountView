# TextCountView
TextCountView is a view to show text size limit with high light and animation which is based on TextView. It supports users' custom style.

![image](/screenshot/screenshot_textcountview.png)

# How to use++
**1. Define in xml**

```xml
<com.ev.travelcard.library.TextCountView
        android:id="@+id/tcv_main_count"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:layout_gravity="right"
        android:textSize="30px"
        android:textColor="#333333"
        tcv:maxCount="20"
        tcv:highlightColor="#e33124"
        tcv:oor_tip_style="highlight|animate"/>
```

**2. Or in code**

```java
TextCountView countTextView = new TextCountView(this);
        countTextView.setMaxCount(50);
        countTextView.setHighlightColor(Color.RED);
        countTextView.setSeperator(" / ");
        countTextView.setOorTipStyle(
                TextCountView.TIP_STYLE_STOCK 
                        | TextCountView.TIP_STYLE_ANIMATE 
                        | TextCountView.TIP_STYLE_HIGHLIGHT);
```

**Attributes:**

attr | description
---|---
maxCount | max size of text
numSeperator | seperator of current count and max count
oor_tip_style | tip style when current count is out of range
highlightColor | color of high light
