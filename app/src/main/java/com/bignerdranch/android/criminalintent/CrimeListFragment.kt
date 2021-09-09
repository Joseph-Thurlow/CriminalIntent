package com.bignerdranch.android.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    /**
     * Required interface for hosting activities
     */
    //Gives the ability for crimeListFragment to call function on its hosting activity.
    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null

    //Lateinit lets the value be assigned later.
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    //CrimeListViewModel is initialized to the variable using the supplied lambda.
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        //The fragment.onAttach(Context) lifecycle function is called when a fragment is attached
        //to an activity.
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    //Loads the fragment_crime_list.xml layout.
    //Finds the recycler view in the layout file.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView =
            view.findViewById(R.id.crime_recycler_view) as RecyclerView
        //LayoutManager is required for RecyclerView to work.
        //The LayoutManager positions every item and also defines how scrolling works.
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //The observer function is used to register an observer on the LiveData instance
        //and tie the life of the observation to the life of another component.
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            { crimes -> //This code block is executed whenever the LiveData's list of crimes is updates.
                crimes?.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    //ViewHolder that extends from RecyclerView.ViewHolder.
    //The recyclerView expects an item view to be wrapped in an instance of ViewHolder.
    //A ViewHolder stores a reference to an item’s view
    private inner class CrimeHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var crime: Crime
        //Finds title and date text views in itemView's hierarchy when an instance is created.
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }

        //Binds the crime holder to a crime
        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()

            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        //Pressing a crime notifies the hosting activity via the Callbacks interface.
        override fun onClick(v: View?) {
            callbacks?.onCrimeSelected(crime.id)
        }
    }

    private inner class CrimeAdapter(var crimes : List<Crime>)
        : RecyclerView.Adapter<CrimeHolder>() {

        //Creates the view, wraps it in a view holder and returns the result.
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        //returns the size of crimes
        override fun getItemCount() = crimes.size

        //Populates the given holder with the crime from a certain position.
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }
    }

    //Activities can call the newInstance function to get an instance of this fragment.
    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

}