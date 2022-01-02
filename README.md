# Weather report app 

### Foreword

This API has been coded as a good way for me to play with Jetpack compose. 

This helped me discover or practice on some concept as Stateflows, or coroutines.

### Short description

This API is supposed to show a first screen (called Home) with a button which lead the user to an another screen called Weather Report. 

This second activity will show a progress Bar, from 0% to 100%.

During this time (which takes 60 seconds), an API call is made every 10 seconds.

When it's over, a simple (but clear & readable) datatable shows weather report for different cities.

The user can redo this weather report generation by clicking on a button at the bottom of the screen. 

Every 6 seconds, a message is updated above this button, without any time out.


### Make it work (**IMPORTANT**)

The API KEY has been removed for security reasons and must be filled in `data/constants.kt`.

A key can be grabbed straight out of openweathermap.com (sign up & check your emails :-)

If this is not done, all API calls will throw IOException and the generation will be stopped...
