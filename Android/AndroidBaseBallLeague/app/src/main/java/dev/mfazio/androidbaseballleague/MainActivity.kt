package dev.mfazio.androidbaseballleague

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dev.mfazio.androidbaseballleague.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //using view binding
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //use Toolbar with Activity
        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(binding.toolbar)

        //finding containerFragment
        //NavHostFragment предоставляет область в вашем макете для автономной навигации.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment

        //Каждый NavHostFragment имеет NavController, который определяет допустимую навигацию внутри узла навигации.
        // Это включает в себя граф навигации, а также состояние навигации, такое как текущее местоположение и задний
        // стек, которые будут сохранены и восстановлены вместе с самим NavHostFragment.
        this.navController = navHostFragment.navController

        //setupWithNavController() - Настраивает NavigationView для использования с NavController. Это вызовет
        // android.view.MenuItem.onNavDestinationSelected при выборе пункта меню.
        //Выбранный элемент в NavigationView будет автоматически обновляться при изменении пункта назначения.
        binding.navView.setupWithNavController(this.navController)

        //AppBarConfiguration() - configure the AppBar
        //send all the classes (from bottom_nav menu) to constructor, which shouldn't display
                // their label (from nav_graph.xml) on the appBar with a back arrow
        //also we can write tne names of the classes instead of sending bottom_nav menu
        this.appBarConfiguration = AppBarConfiguration(binding.navView.menu, binding.drawerLayout)

        //By calling this method, the title of the fragment in the action bar will automatically be updated when the
                // destination changes (assuming there is a valid label)
        //Sets up the ActionBar returned by AppCompatActivity.getSupportActionBar for use with a NavController.
        setupActionBarWithNavController(this.navController, appBarConfiguration)

    }
    //Allows to nav_drawer button to work
    //Этот метод вызывается всякий раз, когда пользователь выбирает перемещение вверх по иерархии
    // действий вашего приложения из панели действий.
    override fun onSupportNavigateUp() : Boolean =
        (this::navController.isInitialized && this.navController.navigateUp(this.appBarConfiguration))
                || super.onSupportNavigateUp()
}