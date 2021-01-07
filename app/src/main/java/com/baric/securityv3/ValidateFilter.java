package com.baric.securityv3;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//removes unwanted chars for ssx hacking
public class ValidateFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            for (int i = start; i < end; i++) {
                String checkMe = String.valueOf(source.charAt(i));
                Pattern pattern = Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890._=+$%#@&*?^:]*");
                Matcher matcher = pattern.matcher(checkMe);
                boolean valid = matcher.matches();
                if (!valid) {
                    return source.subSequence(0, source.length()-1);
                }
            }
        }catch (Exception e){
            
        }
        
        
        return null;
    }
}
