/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.view;

import android.os.Bundle;

public interface Container {
    void onSaveInstanceState(Bundle outState);

    void onCreate(Bundle savedInstanceState);

    boolean onBackPressed();
}
