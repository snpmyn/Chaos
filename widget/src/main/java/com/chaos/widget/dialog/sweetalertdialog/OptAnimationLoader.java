package com.chaos.widget.dialog.sweetalertdialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created on 2017/11/3.
 *
 * @author xxx
 * @desc OptAnimationLoader
 */
public class OptAnimationLoader {
    @SuppressLint("NewApi")
    public static Animation loadAnimation(@NonNull Context context, int id) throws Resources.NotFoundException {
        try (XmlResourceParser parser = context.getResources().getAnimation(id)) {
            return createAnimationFromXml(context, parser);
        } catch (XmlPullParserException | IOException ex) {
            throw new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(id), ex);
        }
    }

    private static Animation createAnimationFromXml(Context c, XmlPullParser parser) throws XmlPullParserException, IOException {
        return createAnimationFromXml(c, parser, null, Xml.asAttributeSet(parser));
    }

    private static Animation createAnimationFromXml(Context c, @NonNull XmlPullParser parser, AnimationSet parent, AttributeSet attrs) throws XmlPullParserException, IOException {
        Animation anim = null;
        // Make sure we are on a start tag.
        int type;
        int depth = parser.getDepth();
        while ((((type = parser.next()) != XmlPullParser.END_TAG) || (parser.getDepth() > depth)) && (type != XmlPullParser.END_DOCUMENT)) {
            if (type != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if ("set".equals(name)) {
                anim = new AnimationSet(c, attrs);
                createAnimationFromXml(c, parser, (AnimationSet) anim, attrs);
            } else if ("alpha".equals(name)) {
                anim = new AlphaAnimation(c, attrs);
            } else if ("scale".equals(name)) {
                anim = new ScaleAnimation(c, attrs);
            } else if ("rotate".equals(name)) {
                anim = new RotateAnimation(c, attrs);
            } else if ("translate".equals(name)) {
                anim = new TranslateAnimation(c, attrs);
            } else {
                try {
                    anim = (Animation) Class.forName(name).getConstructor(Context.class, AttributeSet.class).newInstance(c, attrs);
                } catch (Exception te) {
                    throw new RuntimeException("Unknown animation name: " + parser.getName() + " error: " + te.getMessage());
                }
            }
            if (null != parent) {
                parent.addAnimation(anim);
            }
        }
        return anim;
    }
}
