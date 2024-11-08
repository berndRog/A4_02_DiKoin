package de.rogallab.mobile.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import de.rogallab.mobile.ui.people.PeopleViewModel
import de.rogallab.mobile.ui.people.PersonValidator
import de.rogallab.mobile.ui.people.composables.PersonScreen
import de.rogallab.mobile.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

class MainActivity : BaseActivity(TAG) {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      setContent {
         KoinContext {

            val viewModel: PeopleViewModel = koinViewModel()
            val validator: PersonValidator = koinInject()

            AppTheme {
               Surface {
                  PersonScreen(
                     viewModel,
                     validator = validator,
                     isInputMode = true
                  )
//             PeopleListScreen(
//                viewModel = viewModel
//             )
               }
            }
         }
      }
   }

   companion object {
      private const val TAG = "<-MainActivity"
   }
}

