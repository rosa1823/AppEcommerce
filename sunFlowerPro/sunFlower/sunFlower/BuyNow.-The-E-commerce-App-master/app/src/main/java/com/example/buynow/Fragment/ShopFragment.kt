package com.example.buynow.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buynow.Adapter.CategoryAdapter
import com.example.buynow.Adapter.CoverProductAdapter

import com.example.buynow.Model.Category
import com.example.buynow.Model.Product

import com.example.buynow.Model.Shop
import com.example.buynow.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException


class ShopFragment : Fragment() {


    lateinit var cateList:ArrayList<Category>
    lateinit var coverProduct:ArrayList<Product>

    lateinit var categoryAdapter: CategoryAdapter
    lateinit var coverProductAdapter: CoverProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val coverRecView_shopFrag : RecyclerView = view.findViewById(R.id.coverRecView_shopFrag)
        val categoriesRecView : RecyclerView = view.findViewById(R.id.categoriesRecView)


        cateList = arrayListOf()
        coverProduct = arrayListOf()

        setCoverData()
        setCategoryData()

        coverRecView_shopFrag.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        coverRecView_shopFrag.setHasFixedSize(true)
        coverProductAdapter = CoverProductAdapter(activity as Context, coverProduct )
        coverRecView_shopFrag.adapter = coverProductAdapter


        categoriesRecView.layoutManager = GridLayoutManager(context,2,LinearLayoutManager.VERTICAL,false)
        categoriesRecView.setHasFixedSize(true)
        categoryAdapter = CategoryAdapter(activity as Context, cateList )
        categoriesRecView.adapter = categoryAdapter


        return view
    }

    private fun setCategoryData() {
        cateList.add(Category("Collares","https://i.pinimg.com/564x/ae/98/58/ae9858af20ad7c125a2194d69131df8c.jpg"))
        cateList.add(Category("Accesorios","https://i.pinimg.com/564x/30/d1/17/30d117d5fd6e48c6720d47caf5d7d8bb.jpg"))
        cateList.add(Category("Aretes","https://i.pinimg.com/564x/ed/90/0d/ed900d2a3f315a7600e4dcc7b7bf9aa8.jpg"))
        cateList.add(Category("Anillos","https://i.pinimg.com/564x/f5/08/7e/f5087efdfab521d3c18217509e053b30.jpg"))

    }


    fun getJsonData(context: Context, fileName: String): String? {

        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun setCoverData() {

        val jsonFileString = context?.let {

            getJsonData(it, "CoverProducts.json")
        }
        val gson = Gson()

        val listCoverType = object : TypeToken<List<Product>>() {}.type

        var coverD: List<Product> = gson.fromJson(jsonFileString, listCoverType)

        coverD.forEachIndexed { idx, person ->
            coverProduct.add(person)

        }


    }


}


