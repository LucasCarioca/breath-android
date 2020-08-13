package net.lucasdesouza.countmybreaths.ui.info

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_info.*
import net.lucasdesouza.countmybreaths.R

class InfoFragment : Fragment(R.layout.fragment_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        info_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                help_page.visibility = View.GONE
                pet_health_page.visibility = View.GONE
                about_page.visibility = View.GONE
                when(tab) {
                    info_tabs.getTabAt(0) -> help_page.visibility = View.VISIBLE
                    info_tabs.getTabAt(1) -> pet_health_page.visibility = View.VISIBLE
                    info_tabs.getTabAt(2) -> about_page.visibility = View.VISIBLE
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                print(tab.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                print(tab.toString())
            }
        })
    }

}