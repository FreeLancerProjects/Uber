package com.semicolon.uber.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.semicolon.uber.Adapters.ViewPagerAdapter;
import com.semicolon.uber.Fragments.Fragment_Email;
import com.semicolon.uber.Fragments.Fragment_Image;
import com.semicolon.uber.Fragments.Fragment_Location;
import com.semicolon.uber.Fragments.Fragment_Name;
import com.semicolon.uber.Fragments.Fragment_Phone;
import com.semicolon.uber.Fragments.Fragment_Username_Password;
import com.semicolon.uber.Models.RegisterModel;
import com.semicolon.uber.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private ViewPager pager;
    private TabLayout tab;
    private Button backBtn,nextBtn;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private RegisterModel registerModel;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerModel = RegisterModel.getInstance();
        initView();





    }

    private void initView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(Fragment_Image.getInstance());
        fragmentList.add(Fragment_Name.getInstance());
        fragmentList.add(Fragment_Email.getInstance());
        fragmentList.add(Fragment_Phone.getInstance());
        fragmentList.add(Fragment_Location.getInstance());
        fragmentList.add(Fragment_Username_Password.getInstance());
        title = findViewById(R.id.title);
        pager=findViewById(R.id.pager);
        tab=findViewById(R.id.tab);
        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        tab.setClickable(false);
        tab.setupWithViewPager(pager);


        adapter.AddFragments(fragmentList);
        pager.setAdapter(adapter);
        pager.beginFakeDrag();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position==0)
                {
                    title.setText("Image profile");
                    Fragment_Image fragment_image  = (Fragment_Image) fragmentList.get(0);


                }else if (position==1)
                {
                    title.setText("Name");
                    Fragment_Name fragment_name = (Fragment_Name) fragmentList.get(1);
                    fragment_name.getName();


                }
                else if (position==2)
                {
                    title.setText("Email");
                    Fragment_Email fragment_email = (Fragment_Email) fragmentList.get(2);
                    fragment_email.getEmail();
                    String email = registerModel.getEmail();


                }else if (position==3)
                {
                    title.setText("Phone number");
                    Fragment_Phone fragment_phone = (Fragment_Phone) fragmentList.get(3);
                    fragment_phone.getPhone();
                    String phone = registerModel.getPhone();

                }else if (position==4)
                {
                    title.setText("Location");
                    Fragment_Location fragment_location = (Fragment_Location) fragmentList.get(4);
                    fragment_location.getCountry();
                    fragment_location.getCity();
                    String country = registerModel.getCountry();
                    String city = registerModel.getCity();


                }else if (position==5)
                {
                    title.setText("Authentication");
                    Fragment_Username_Password fragment_username_password = (Fragment_Username_Password) fragmentList.get(5);
                    fragment_username_password.getUserName();
                    fragment_username_password.getPassword();

                    String userName = registerModel.getUser_name();
                    String password = registerModel.getPassword();


                }
                if (pager.getCurrentItem()<=fragmentList.size()-1)
                {

                ///////////////////////////////
                if (position==0)
                {
                    backBtn.setVisibility(View.INVISIBLE);
                }else
                {
                    backBtn.setVisibility(View.VISIBLE);
                    if (position==fragmentList.size()-1)
                    {
                        nextBtn.setText("Get Started");
                        nextBtn.setTextColor(ContextCompat.getColor(RegisterActivity.this,R.color.reg_btn_color));
                        nextBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    }else
                        {

                            nextBtn.setText("Next");
                            nextBtn.setTextColor(ContextCompat.getColor(RegisterActivity.this,R.color.gray1));
                            nextBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.next,0);


                        }

                }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pager.getCurrentItem()<=fragmentList.size()-1)
                {
                    if (pager.getCurrentItem()==0)
                    {
                        Fragment_Image fragment_image  = (Fragment_Image) fragmentList.get(0);
                        String encoded_image = registerModel.getImage();
                        if (TextUtils.isEmpty(encoded_image))
                        {
                            fragment_image.setImageError();
                        }else
                            {
                                pager.setCurrentItem(pager.getCurrentItem()+1);

                            }
                    }
                    else if (pager.getCurrentItem()==1)
                    {

                        Fragment_Name fragment_name = (Fragment_Name) fragmentList.get(1);
                        fragment_name.getName();
                        String name = registerModel.getName();
                        if (TextUtils.isEmpty(name))
                        {
                            fragment_name.setNameError();
                        }else
                        {
                            fragment_name.removeError();
                            pager.setCurrentItem(pager.getCurrentItem()+1);

                        }
                    }

                    else if (pager.getCurrentItem()==2)
                    {

                        Fragment_Email fragment_email = (Fragment_Email) fragmentList.get(2);
                        fragment_email.getEmail();
                        String email = registerModel.getEmail();
                        if (TextUtils.isEmpty(email))
                        {
                            fragment_email.setEmailEmpty();
                        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            fragment_email.setEmailInvalid();
                        }
                        else
                        {
                            fragment_email.removeError();
                            pager.setCurrentItem(pager.getCurrentItem()+1);

                        }
                    }

                    else if (pager.getCurrentItem()==3)
                    {

                        Fragment_Phone fragment_phone = (Fragment_Phone) fragmentList.get(3);
                        fragment_phone.getPhone();
                        String phone = registerModel.getPhone();
                        if (TextUtils.isEmpty(phone))
                        {
                            fragment_phone.setPhoneError();
                        }
                        else
                        {
                            fragment_phone.removeError();
                            pager.setCurrentItem(pager.getCurrentItem()+1);

                        }
                    }

                    else if (pager.getCurrentItem()==4)
                    {

                        Fragment_Location fragment_location = (Fragment_Location) fragmentList.get(4);
                        fragment_location.getCountry();
                        fragment_location.getCity();
                        String country = registerModel.getCountry();
                        String city = registerModel.getCity();
                        if (TextUtils.isEmpty(country))
                        {
                            fragment_location.setCountryError();
                        }else if (TextUtils.isEmpty(city))
                        {
                            fragment_location.removeCountryError();
                            fragment_location.setCityError();
                        }
                        else
                        {
                            fragment_location.removeCountryError();
                            fragment_location.removecityError();
                            pager.setCurrentItem(pager.getCurrentItem()+1);

                        }
                    }
                    else if (pager.getCurrentItem()==5)
                    {

                        Fragment_Username_Password fragment_username_password = (Fragment_Username_Password) fragmentList.get(5);
                        fragment_username_password.getUserName();
                        fragment_username_password.getPassword();

                        String userName = registerModel.getUser_name();
                        String password = registerModel.getPassword();
                        if (TextUtils.isEmpty(userName))
                        {
                            fragment_username_password.setUserNameError();
                        }else if (TextUtils.isEmpty(password))
                        {
                            fragment_username_password.removeUserNameError();
                            fragment_username_password.setPasswordError();
                        }else if (password.length()<5)
                        {
                            fragment_username_password.removeUserNameError();
                            fragment_username_password.setPasswordShort();

                        }
                        else
                        {
                            fragment_username_password.removeUserNameError();
                            fragment_username_password.removePasswordError();
                            if (TextUtils.isEmpty(registerModel.getImage()))
                            {
                                pager.setCurrentItem(0);
                            }else if (TextUtils.isEmpty(registerModel.getName()))
                            {
                                pager.setCurrentItem(1);

                            }
                            else if (TextUtils.isEmpty(registerModel.getEmail()))
                            {
                                pager.setCurrentItem(2);

                            }else if (TextUtils.isEmpty(registerModel.getPhone()))
                            {
                                pager.setCurrentItem(3);

                            }else if (TextUtils.isEmpty(registerModel.getCountry())||TextUtils.isEmpty(registerModel.getCity()))
                            {
                                pager.setCurrentItem(4);

                            }else if (TextUtils.isEmpty(registerModel.getUser_name())||TextUtils.isEmpty(registerModel.getPassword()))
                            {
                                pager.setCurrentItem(5);

                            }else
                            {
                                Log.e("image",registerModel.getImage());
                                Log.e("name",registerModel.getName());
                                Log.e("email",registerModel.getEmail());
                                Log.e("phone",registerModel.getPhone());
                                Log.e("country",registerModel.getCountry());
                                Log.e("city",registerModel.getCity());
                                Log.e("username",registerModel.getUser_name());
                                Log.e("password",registerModel.getPassword());
                            }

                        }
                    }
                }

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pager.getCurrentItem()>0)
                {
                    pager.setCurrentItem(pager.getCurrentItem()-1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment:fragmentList)
        {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


}
