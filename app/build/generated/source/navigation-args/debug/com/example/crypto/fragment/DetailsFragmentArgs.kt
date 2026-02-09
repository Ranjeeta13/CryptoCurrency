package com.example.crypto.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.example.crypto.models.CryptoCurrency
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class DetailsFragmentArgs(
  public val `data`: CryptoCurrency? = null,
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
    val result = Bundle()
    if (Parcelable::class.java.isAssignableFrom(CryptoCurrency::class.java)) {
      result.putParcelable("data", this.data as Parcelable?)
    } else if (Serializable::class.java.isAssignableFrom(CryptoCurrency::class.java)) {
      result.putSerializable("data", this.data as Serializable?)
    }
    return result
  }

  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    if (Parcelable::class.java.isAssignableFrom(CryptoCurrency::class.java)) {
      result.set("data", this.data as Parcelable?)
    } else if (Serializable::class.java.isAssignableFrom(CryptoCurrency::class.java)) {
      result.set("data", this.data as Serializable?)
    }
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): DetailsFragmentArgs {
      bundle.setClassLoader(DetailsFragmentArgs::class.java.classLoader)
      val __data : CryptoCurrency?
      if (bundle.containsKey("data")) {
        if (Parcelable::class.java.isAssignableFrom(CryptoCurrency::class.java) ||
            Serializable::class.java.isAssignableFrom(CryptoCurrency::class.java)) {
          __data = bundle.get("data") as CryptoCurrency?
        } else {
          throw UnsupportedOperationException(CryptoCurrency::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
      } else {
        __data = null
      }
      return DetailsFragmentArgs(__data)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): DetailsFragmentArgs {
      val __data : CryptoCurrency?
      if (savedStateHandle.contains("data")) {
        if (Parcelable::class.java.isAssignableFrom(CryptoCurrency::class.java) ||
            Serializable::class.java.isAssignableFrom(CryptoCurrency::class.java)) {
          __data = savedStateHandle.get<CryptoCurrency?>("data")
        } else {
          throw UnsupportedOperationException(CryptoCurrency::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
      } else {
        __data = null
      }
      return DetailsFragmentArgs(__data)
    }
  }
}
