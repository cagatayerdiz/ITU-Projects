package tr.edu.itu.cs.db.Form;

import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;


public final class CaptchaForm<T> extends Form<T> {

    CaptchaImageResource captchaImageResource;
    String imagePass;
    ValueMap properties;

    public CaptchaForm(String id) {
        super(id);
        imagePass = randomString(5, 7);
        properties = new ValueMap();
        captchaImageResource = new CaptchaImageResource(imagePass);
        this.add(new NonCachingImage("captchaImage", captchaImageResource));
        add(new RequiredTextField<String>("Cpassword",
                new PropertyModel<String>(properties, "Cpassword")) {
            @Override
            protected final void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);
                // clear the field after each render
                tag.put("value", "");
            }
        });
    }

    private static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    private static String randomString(int min, int max) {
        int num = randomInt(min, max);
        byte b[] = new byte[num];
        for (int i = 0; i < num; i++)
            b[i] = (byte) randomInt('0', '9');
        return new String(b);
    }

    String getPassword() {
        return properties.getString("Cpassword");
    }

    public boolean work() {
        boolean check = false;
        if (imagePass.equals(getPassword())) {
            check = true;
        }
        return check;
    }

}
