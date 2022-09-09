package com.tian.counterta

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import com.tian.counterta.data.TotalTrip
import com.tian.counterta.data.TripDatabase
import com.tian.counterta.databinding.ActivityMainBinding
import com.tian.counterta.databinding.DialogPinBinding
import com.tian.counterta.utils.Pref
import com.tian.counterta.utils.Pref.FARE_TRANSACTION
import com.tian.counterta.utils.Pref.FARE_TRANSACTION_PAYMENT
import com.tian.counterta.utils.obtainViewModel
import com.tian.counterta.utils.toFormatRupiah
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by obtainViewModel(TripViewModel::class)
    private lateinit var dialogPinBinding: DialogPinBinding
    private lateinit var dialog : Dialog

    lateinit var db: TripDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Pref.init(this)
        Pref.setInt(FARE_TRANSACTION, 2500)

        onView()
        setupDB()
    }



    private fun onView() = binding.run {
        btnCard1.setOnClickListener { tapThreeTripFree("123456") }
        btnCard2.setOnClickListener { tapThreeTripFree("098765") }
        btnCard3.setOnClickListener { tapThreeTripFree("564738") }
    }

    var tripPayment = 0
    private var trip: Int by Delegates.observable(1) { _, old, new ->
        tripPayment = new
        Log.e("TAG", "initData tripDelegate: $old $new $tripPayment", )
    }
    @SuppressLint("SetTextI18n")
    private fun tapThreeTripFree(cardNumber : String) = binding.run {


        // ON READ
        tripPayment++
        if (tripPayment<=2)  Pref.setInt(FARE_TRANSACTION, 2500)
        else if (tripPayment>=6) Pref.setInt(FARE_TRANSACTION, 3500)
        else Pref.setInt(FARE_TRANSACTION, 0)

        //simulation send to payment
        val fare = Pref.getInt(FARE_TRANSACTION)
        Log.e("TAG", "initData onRead: tripPayment $tripPayment | fare $fare", )
        tvFareTransaction.text = fare.toString()



        // ON RESULT

            val checkExistsNumberCard = viewModel.checkExistsNumberCard(cardNumber)
            trip = if(!checkExistsNumberCard){
                tripPayment = 1
                1
            } else {
                val getTrip =  db.tripDao().getTripById(cardNumber)

                Log.e("TAG", "initData: getTrip ${getTrip.toInt() + 1} ", )
                getTrip.toInt() + 1
            }

            val tripData = TotalTrip(cardNumber, trip.toString())
            viewModel.insert(tripData)

            Log.e("TAG", "initData: trip $trip | $checkExistsNumberCard ", )




            //update layout tap
//            CoroutineScope(Dispatchers.Main).launch {
                tvTitleTrip.text = "Tap ke $trip"
                updateFare()
//            }



    }

//    @SuppressLint("SetTextI18n")
//    private fun tapThreeTripFree(cardNumber : String) = binding.run {
//
//
//        CoroutineScope(Dispatchers.IO).launch{
//            val checkExistsNumberCard = db.tripDao().checkExistsNumberCard(cardNumber)
//            trip = if(!checkExistsNumberCard){
//                1
//            } else {
//                val getTrip =  db.tripDao().getTripById(cardNumber)
//                Log.e("TAG", "initData: getTrip ${getTrip.toInt() + 1} ", )
//                getTrip.toInt() + 1
//            }
//
//            val tripData = TotalTrip(cardNumber, trip.toString())
//            db.tripDao().insert(tripData)
//
//            Log.e("TAG", "initData: trip $trip | $checkExistsNumberCard ", )
//
//
//
//
//
//            //simalasi send to payment
//            CoroutineScope(Dispatchers.IO).launch {
//                val fare = Pref.getInt(FARE_TRANSACTION)
//                Log.e("TAG", "initData onRead: trip $trip | fare $fare", )
//                CoroutineScope(Dispatchers.Main).launch{tvFareTransaction.text = fare.toString()}
//            }
//
//            //update layout tap
//            CoroutineScope(Dispatchers.Main).launch {
//                tvTitleTrip.text = "Tap ke $trip"
//                updateFare()
//            }
//        }
//
//
//    }


    private fun setupDB() {
        db = Room.databaseBuilder(applicationContext, TripDatabase::class.java, "trip-db").build()
        CoroutineScope(Dispatchers.IO).launch {
            db.tripDao().deleteAllTrip()
            Log.e("TAG", "initData: DELETETRIP", )
        }
    }

    private fun updateFare() = binding.run {
        tvFare.text = getString(R.string.fare, " ${Pref.getInt(FARE_TRANSACTION).toFormatRupiah()}")
        tvFare.setOnClickListener {
            dialogPinBinding = DialogPinBinding.inflate(layoutInflater)
            dialog = Dialog(this@MainActivity)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(dialogPinBinding.root)
//            dialog.setCancelable(false)
            dialogPinBinding.titleDialog.text = "PERGANTIAN TANGGAL"
            dialogPinBinding.edPin.visibility = View.GONE
//            dialogPinBinding.edPin.hint = "00000"
//            dialogPinBinding.edPin.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
            dialogPinBinding.btOk.setOnClickListener {
//                val getFare = dialogPinBinding.edPin.text.toString()
//                if(getFare.isNotEmpty()){
                Pref.setInt(FARE_TRANSACTION, 2500)
                binding.tvFare.text = getString(R.string.fare, " ${Pref.getInt(FARE_TRANSACTION).toFormatRupiah()}")
                trip = 1
                tvTitleTrip.text = ""
                CoroutineScope(Dispatchers.IO).launch {
                    db.tripDao().deleteAllTrip()
                    Log.e("TAG", "initData: DELETETRIP", )
                }
                dialog.dismiss()
//                } else if (getFare.isEmpty()) showToast("PIN Belum Terisi")

            }
            dialogPinBinding.btCancel.visibility = View.GONE
            dialog.show()
        }

    }
}

