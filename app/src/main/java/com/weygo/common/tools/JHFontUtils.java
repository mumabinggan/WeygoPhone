package com.weygo.common.tools;

import android.content.Context;
import android.graphics.Typeface;

import com.weygo.weygophone.R;

/**
 * Created by muma on 2016/12/14.
 */

public class JHFontUtils {

    public enum JHFontType {
        JHFontDefault,
        JHFontOswaldBold,
        JHFontOswaldBoldItalic,
        JHFontOswaldDemiBoldItalic,
        JHFontOswaldDemiBold,
        JHFontOswaldExtraLightIntalic,
        JHFontOswaldExtraLight,
        JHFontOswaldHeavy,
        JHFontOswaldHeavyItalic,
        JHFontOswaldLight,
        JHFontOswaldLightItalic,
        JHFontOswaldMediumItalic,
        JHFontOswaldMedium,
        JHFontOswaldRegular,
        JHFontOswaldRegularItalic,
        JHFontOswaldStencil;
    }

    static public Typeface getTypeface(Context context, JHFontType fontType) {
        String fontName = "fonts/";
        switch (fontType) {
            case JHFontDefault:
                fontName += "";
                break;
            case JHFontOswaldBold:
                fontName += "Oswald-Bold";
                break;
            case JHFontOswaldBoldItalic:
                fontName += "Oswald-BoldItalic";
                break;
            case JHFontOswaldDemiBold:
                fontName += "Oswald-Demi-Bold";
                break;
            case JHFontOswaldDemiBoldItalic:
                fontName += "Oswald-Demi-BoldItalic";
                break;
            case JHFontOswaldExtraLight:
                fontName += "Oswald-Extra-Light";
                break;
            case JHFontOswaldHeavy:
                fontName += "Oswald-Heavy";
                break;
            case JHFontOswaldExtraLightIntalic:
                fontName += "Oswald-Extra-LightItalic";
                break;
            case JHFontOswaldHeavyItalic:
                fontName += "Oswald-HeavyItalic";
                break;
            case JHFontOswaldStencil:
                fontName += "Oswald-Stencil";
                break;
            case JHFontOswaldRegular:
                fontName += "Oswald-Regular";
                break;
            case JHFontOswaldRegularItalic:
                fontName += "Oswald-RegularItalic";
                break;
            case JHFontOswaldLight:
                fontName += "Oswald-Light";
                break;
            case JHFontOswaldLightItalic:
                fontName += "Oswald-LightItalic";
                break;
            case JHFontOswaldMedium:
                fontName += "Oswald-Medium";
                break;
            case JHFontOswaldMediumItalic:
                fontName += "Oswald-MediumItalic";
                break;
        }
        fontName += ".ttf";
        return Typeface.createFromAsset(context.getAssets(), fontName);
    }

    public static float font(Context context, float fontSize) {
        return (JHAdaptScreenUtils.deviceDpWidth(context) * fontSize) / 320;
    }
}
