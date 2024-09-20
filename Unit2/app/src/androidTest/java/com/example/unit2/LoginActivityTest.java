package com.example.unit2;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import com.example.unit2.ui.login.LoginActivity;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testInvalidLogin() {
        // Nhập thông tin không hợp lệ
        onView(withId(R.id.username)).perform(replaceText("invalid@user.com"));
        onView(withId(R.id.password)).perform(replaceText("123"));
        onView(withId(R.id.login)).check(ViewAssertions.matches(isNotEnabled()));
    }

    @Test
    public void testSuccessfulLogin() {
        // Nhập thông tin hợp lệ
        onView(withId(R.id.username)).perform(replaceText("valid@user.com"));
        onView(withId(R.id.password)).perform(replaceText("correctpassword"));
        onView(withId(R.id.login)).perform(click());

        // Kiểm tra xem ProgressBar không còn hiển thị
//        onView(withId(R.id.loading)).check(ViewAssertions.matches(not(isDisplayed())));

        // Kiểm tra xem MainActivity có được hiển thị sau khi đăng nhập thành công
        // Giả sử bạn đã chuyển đến MainActivity sau khi đăng nhập thành công
        // Chúng ta có thể kiểm tra các view trong MainActivity để xác nhận điều này
        // Ví dụ, nếu có một FAB (Floating Action Button) trong MainActivity, bạn có thể kiểm tra như sau:
        onView(withId(R.id.fab_add_item)).check(ViewAssertions.matches(isDisplayed()));
    }
}
