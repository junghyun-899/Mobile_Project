package com.example.mobile_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_project.databinding.ActivityMainBinding
import com.example.mobile_project.fragment.HomeFragment
import com.example.mobile_project.fragment.MapFragment
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.example.mobile_project.db.DBHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, HomeFragment()).commit()
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, HomeFragment()).commit()
                    true
                }
                R.id.menu_map -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, MapFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?
    ): Boolean
    {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId){
            R.id.menu_stat -> { showStatistics() }
            R.id.menu_delete_all -> { deleteAllRecords() }
        }
        return true
    }
    private fun showStatistics() {
        val list = DBHelper(this).getAll()
        val count = list.size
        AlertDialog.Builder(this).setTitle("여행 통계")
            .setMessage("총 여행 기록 : $count 개")
            .setPositiveButton("확인", null)
            .show()
    }
    private fun deleteAllRecords() {
        AlertDialog.Builder(this)
            .setTitle("전체 삭제")
            .setMessage("정말 삭제하시겠습니까?")
            .setPositiveButton("삭제")
            { _, _ -> DBHelper(this).deleteAll()
                recreate()
            }
            .setNegativeButton("취소", null)
            .show()
    }
}