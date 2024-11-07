package de.rogallab.mobile

import android.app.Application
import de.rogallab.mobile.domain.utilities.logInfo
import de.rogallab.mobile.ui.people.PersonValidator
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup.onKoinStartup
import org.koin.core.logger.Level

class AppStart : Application() {


   init{
      logInfo(TAG, "init: onKoinStartUp{...}")
      onKoinStartup {
         // Log Koin into Android logger
         androidLogger(Level.DEBUG)
         // Reference Android context
         androidContext(this@AppStart)
         // Load modules
         modules(domainModules, dataModules, uiModules)
      }
   }


   override fun onCreate() {
      super.onCreate()

      logInfo(TAG, "onCreate()")

      // Singletons are initialized here
      // personValidator = PersonValidator.getInstance(applicationContext)

   }

   companion object {
      const val ISINFO = true
      const val ISDEBUG = true
      const val ISVERBOSE = true
      private const val TAG = "<-AppApplication"

      //lateinit var personValidator: PersonValidator
      //   private set

   }
}