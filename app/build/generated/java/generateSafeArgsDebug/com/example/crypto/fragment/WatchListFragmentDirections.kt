package com.example.crypto.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.`annotation`.CheckResult
import androidx.navigation.NavDirections
import com.example.crypto.R
import com.example.crypto.models.CryptoCurrency
import java.io.Serializable
import kotlin.Int
import kotlin.Suppress

public class WatchListFragmentDirections private constructor() {
  private data class ActionWatchListFragmentToDetailsFragment(
    public val `data`: CryptoCurrency? = null,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_watchListFragment_to_detailsFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
        val result = Bundle()
        if (Parcelable::class.java.isAssignableFrom(CryptoCurrency::class.java)) {
          result.putParcelable("data", this.data as Parcelable?)
        } else if (Serializable::class.java.isAssignableFrom(CryptoCurrency::class.java)) {
          result.putSerializable("data", this.data as Serializable?)
        }
        return result
      }
  }

  public companion object {
    @CheckResult
    public fun actionWatchListFragmentToDetailsFragment(`data`: CryptoCurrency? = null): NavDirections = ActionWatchListFragmentToDetailsFragment(data)
  }
}
