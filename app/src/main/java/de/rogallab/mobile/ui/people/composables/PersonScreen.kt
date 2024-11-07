package de.rogallab.mobile.ui.people.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.rogallab.mobile.AppStart
import de.rogallab.mobile.R
import de.rogallab.mobile.domain.utilities.logDebug
import de.rogallab.mobile.domain.utilities.logError

import de.rogallab.mobile.ui.people.PeopleViewModel
import de.rogallab.mobile.ui.people.PersonValidator
import de.rogallab.mobile.ui.people.PersonIntent
import de.rogallab.mobile.ui.people.PersonUiState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonScreen(
   viewModel: PeopleViewModel = koinViewModel(),
   validator: PersonValidator,
   isInputMode: Boolean = true,
   id: String? = null
) {
   // Observe the state of the viewmodel
   val personUiState: PersonUiState
      by viewModel.personUiStateFlow.collectAsStateWithLifecycle()

   val screenTitle =
      if (isInputMode) stringResource(R.string.personInput)
      else stringResource(R.string.personDetail)
   val tag =
      if (isInputMode) "<-PersonInputScreen"
      else "<-PersonDetailScreen"

   // is PersonDetailScreen
   if (!isInputMode) {
      id?.let { it: String ->
         LaunchedEffect(Unit) {
            viewModel.onProcessIntent(PersonIntent.FetchById(it))
         }
      } ?: run {
         logError(tag,"No id for person is given")
      }
   }

   val windowInsets = WindowInsets.systemBars
      .add(WindowInsets.ime)
      .add(WindowInsets.safeGestures)

   Column(modifier = Modifier
      .fillMaxSize()
      .verticalScroll(state = rememberScrollState())
      .padding(windowInsets.asPaddingValues())
      .padding(horizontal = 16.dp)
      .imePadding() //
   ) {
      TopAppBar(
         title = { Text(text = screenTitle) },
         navigationIcon = {
            IconButton(onClick = {
               logDebug(tag, "Up (reverse) -> PeopleListScreen")
            }) {
               Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                  contentDescription = stringResource(R.string.back))
            }
         }
      )

      InputName(
         name = personUiState.person.firstName,          // State ↓
         onNameChange = { firstName: String ->           // Event ↑
         viewModel.onProcessIntent(PersonIntent.FirstNameChange(firstName)) },
         label = stringResource(R.string.firstName),     // State ↓
         validateName = validator::validateFirstName,    // parameter
      )
      InputName(
         name = personUiState.person.lastName,           // State ↓
         onNameChange = { lastName: String ->            // Event ↑
            viewModel.onProcessIntent(PersonIntent.LastNameChange(lastName)) },
         label = stringResource(R.string.lastName),             // State ↓
         validateName = validator::validateLastName,     // parameter
      )
      InputEmail(
         email = personUiState.person.email,             // State ↓
         onEmailChange = { email:String ->               // Event ↑
            viewModel.onProcessIntent(PersonIntent.EmailChange(email)) },
         validateEmail = validator::validateEmail        // parameter
      )
      InputPhone(
         phone = personUiState.person.phone,             // State ↓
         onPhoneChange = { phone:String ->               // Event ↑
            viewModel.onProcessIntent(PersonIntent.PhoneChange(phone)) },
         validatePhone = validator::validatePhone        // parameter
      )
   } // Column
}