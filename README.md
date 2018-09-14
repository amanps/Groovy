# Groovy
Work in progress. 

An Android application for discovery of Movies and TV Shows built using the TMDB API.

* Built purely in Kotlin
* Built using the MVP architecture with Dagger, Rx and several key Android libraries / APIs. 

<img src="https://github.com/amanps/Groovy/blob/master/images/Screenshot1.png" width="360"> <img src="https://github.com/amanps/Groovy/blob/master/images/Screenshot2.png" width="360">
<img src="https://github.com/amanps/Groovy/blob/master/images/Screenshot3.png" width="360"> <img src="https://github.com/amanps/Groovy/blob/master/images/Screenshot4.png" width="360">

Application structure:

```bash
├── app
│   ├── data
│       ├── model
│           ├── CastCrewResponseModel.kt
│           ├── CastModel.kt
│           ├── DiscoverApiResponse.kt
│           ├── GenreResponseModel.kt
│           ├── Program.kt
│       ├── network
│           ├── ProgramService.kt
│       ├── DataManager.kt
│   ├── di
│       ├── component
│           ├── ActivityComponent.kt
│           ├── ApplicationComponent.kt
│       ├── module
│           ├── ApplicationModule.kt
│           ├── NetworkModule.kt
│       ├──  	ActivityScope.kt
│   ├── ui
│       ├── base
│           ├── BaseActivity.kt
│           ├── BasePresenter.kt
│           ├── BaseView.kt
│       ├── common
│           ├── HorizontalProgramRecyclerAdapter.kt
│       ├── detail
│           ├── CastRecyclerAdapter.kt
│           ├── DetailActivity.kt
│           ├── DetailPresenter.kt
│           ├── DetailView.kt
│       ├── home
│           ├── BannerPagerAdapter.kt
│           ├── HomeActivity.kt
│           ├── HomeAdapter.kt
│           ├── HomeListSectionModel.kt
│           ├── HomePresenter.kt
│           ├── HomeView.kt
│   ├── util
│       ├── Constants.kt
│       ├── NetworkUtils.kt
│       ├── Util.kt
```
