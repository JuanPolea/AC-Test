package com.jfmr.ac.test.presentation.ui.component.extensions

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

object ListExtensions {
    private data class PagingPlaceholderKey(private val index: Int) : Parcelable {
        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(index)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<PagingPlaceholderKey> = object : Parcelable.Creator<PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) = PagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
            }
        }
    }

    internal fun <T : Any> LazyGridScope.gridItems(
        items: LazyPagingItems<T>,
        key: ((item: T) -> Any)? = null,
        itemContent: @Composable LazyGridItemScope.(item: T?) -> Unit,
    ) {
        items(
            count = items.itemCount,
            key = if (key != null) { index ->
                items[index]?.let(key) ?: PagingPlaceholderKey(index)
            } else {
                null
            }
        ) { index ->
            itemContent(items[index])
        }
    }
}
