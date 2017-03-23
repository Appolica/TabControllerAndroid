# TabControllerAndroid
This library provides an easy to use API for switching between fragments that share the same container. Implementing tabs, such as the ones Instagram has at the bottom of the layout, is a much simpler goal to achieve when using **TabController**.<br>
Tabs can be switched by using either show/hide or attach/detach. You can even provide your own implementation of the way tabs are being shown.

## Example

There are two ways of using this library. One is to use `TabControllerFragment`.  It encapsulates all the fragments and their container into a single fragment. 

You can use it like any other fragment by adding it to the xml layout:
```xml
<fragment
        android:id="@+id/fragment"
        android:layout_width="0dp"
        android:layout_height="0dp"
        class="com.appolica.tabcontroller.fragment.TabControllerFragment"/>
```

or instantiating it and adding it by yourself:

```java
final TabControllerFragment controllerFragment = new TabControllerFragment();
getSupportFragmentManager()
                .beginTransaction()
                .add(conainerId, controllerFragment)
                .commitNow();
```

Obtain the TabController instance simply like this:
```java
final TabController tabController = controllerFragment.getTabController();
```

---
The other way is to provide your own fragment container. If you choose to use this, you have to create the TabController instance by yourself:

```java
final TabController tabController = new TabController(getSupportFragmentManager(), R.id.container);
```
---

It doesn't matter whether you use your own container or TabControllerFragment. Once you obtain a TabController instance, everything is the same.

In order to show your fragment you have to use `TabController::switchTo` method. This method accepts an implementation of `FragmentProvider`.
Suppose you have multiple fragments and the first one is called `HomeFragment`. Then you would need something like `HomeFragmentProvider`, that implements `FragmentProvider`.

```java
public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";

    public static HomeFragment getInstance(int tabNum) {
        final HomeFragment fragment = new HomeFragment();

        return tab;
    }

    ...
}
```
```java
class HomeFragmentProvider implements FragmentProvider{
    @Override
    public String getTag() {
        return HomeFragment.TAG;
    }

    @Override
    public Fragment getInstance() {
        return HomeFragment.getInstance();
    }
}
```
Create an instance of `HomeFragmentProvider` and pass it to `TabController::switchTo` in order to show your `HomeFragment`.
```java
final HomeFragmentProvider homeProvider = new HomeFragmentProvider();
tabController.switchTo(homeProvider);
```
The `TabController` will create a new instance (by calling `FragmentProvider::getInstance`) of your fragment if it hasn't been already created. This depends on whether `FragmentManager::findFragmentByTag` returns `null` for the tag, given by `FragmentProvider::getTag`.


## API
