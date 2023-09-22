/**

        ***Please before start change api-key in the gradle.build file to prevent this error***
***Developer accounts are limited to 100 requests over a 24 hour period (50 requests available every 12 hours).***
                    ***Please upgrade to a paid plan if you need more requests***

* Design pattern used in this project MVVM
* Networking file extends ViewModel and consist of one function [get] function with parameters
* url: url for the api
* category: category chosen from the onboard.
* searchWord: optional in Home page [HeadlinesFragment] and needed in [SearchFragment]

* api-key: found in build.gradle file
* the [get] function is generic to be used in the whole app


* BaseActivity: used to continuously check for internet and change app language
* HeadlinesBaseFragment: serves [HeadlinesFragment] [SearchFragment] [FavoriteFragment], used to get online headlines
* from HeadlinesViewModel and return list of HeadlinesModel also this file is used to get stored headlines and categories
* in database using Rooms , so this file has all the functions that these 3 fragments need


* Utils:
* CategoryUtils: used to get the categories that are used in on-boarding screen, i didn't find any api in [https://newsapi.org]
* to get the categories so i add them manually in a list with english and arabic localizations in case if the user want to use
* english or arabic that can be selected in every screen.
* 
* CountryUtils: used to get the countries that are used in on-boarding screen. also i didn't find api for it so i added
* them manually.

* SharedPreferencesUtils:
* used to get and set language and country


* i tries to make independent functions that can be used any where in the code.
  */