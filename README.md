# User management

Single activity Android app that shows informations about random users using [Random User Generator](https://randomuser.me/) REST API.

## Android & 3rd party features used
- Kotlin programming language
- material components & themes
- single activity with one fragment
- MVVM with ViewModel and Flows
- vertical RecyclerView with PagingDataAdapter & DiffUtil
- endless scrolling with PagingDataAdapter and PagingSource, using Paging v3
- coroutines and Flow
- drawer navigation and toolbar menu
- data binding, view binding, binding adapters for loading images
- REST API calls with [Retrofit](https://square.github.io/retrofit/)
- image loading with [Glide](https://github.com/bumptech/glide)
- dependency injection with [Hilt](https://dagger.dev/hilt/)
- retry mechanism in case of *5xx* server errors
- repository & use cases
