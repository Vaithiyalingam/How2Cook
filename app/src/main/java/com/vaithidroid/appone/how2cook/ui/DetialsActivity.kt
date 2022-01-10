package com.vaithidroid.appone.how2cook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.vaithidroid.appone.how2cook.R
import com.vaithidroid.appone.how2cook.adapter.PagerAdapter
import com.vaithidroid.appone.how2cook.ui.fragments.IngredientsFragment
import com.vaithidroid.appone.how2cook.ui.fragments.InstructionFragment
import com.vaithidroid.appone.how2cook.ui.fragments.OverviewFragment
import kotlinx.android.synthetic.main.activity_detials.*

class DetialsActivity : AppCompatActivity() {


    private val args by navArgs<DetialsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detials)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingrediants")
        titles.add("Instruction")

        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}