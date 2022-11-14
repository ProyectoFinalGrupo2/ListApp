package com.ort.listapp.ui.alacena

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.ort.listapp.ListaAppApplication
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentAlacenaBinding
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.AlacenaAdapter
import com.ort.listapp.ui.auth.AuthViewModel
import com.ort.listapp.utils.HelperClass
import com.ort.listapp.utils.HelperClass.showToast

class AlacenaFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private var _binding: FragmentAlacenaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FamilyViewModel by activityViewModels()
    private var productosAlacena :MutableList<ItemLista>? = null

    private lateinit var popup: AlertDialog
    private lateinit var popupBuilder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlacenaBinding.inflate(inflater, container, false)
        productosAlacena = mutableListOf()
        cargarProductos()
        initRecyclerView()
        setHasOptionsMenu(true)
        return binding.root
    }
    private fun initRecyclerView(){
        binding.alacenaProductos.setHasFixedSize(true)
        binding.alacenaProductos.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.alacenaProductos.apply {
            layoutManager = GridLayoutManager(this.context, 2)
        }
        binding.alacenaProductos.adapter =
            productosAlacena?.let {prods->
                AlacenaAdapter(
                    prods,
                    requireContext(),
                    {
                        clickSumaYResta(it, 1)
                    },
                    {
                        clickSumaYResta(it, -1)
                    })
            }

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tool_bar, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var direction: NavDirections? = null
        when (item.itemId) {
            R.id.userConfigButton -> {
                initPopup()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun initPopup() {
        popupBuilder = AlertDialog.Builder(requireContext())
        val popUpView = layoutInflater.inflate(R.layout.popup_configuracion, null)
        val btnCerrar = popUpView.findViewById<ImageView>(R.id.btn_cerrar_popup_config)
        val nombreUsuario = popUpView.findViewById<TextView>(R.id.nombreUsuarioPopup)
        val email = popUpView.findViewById<TextView>(R.id.emailConfigPopup)
        val tfNewEmail = popUpView.findViewById<TextInputLayout>(R.id.tfNewEmailPopup)
        val inputEmail = tfNewEmail.editText
        val btnCambiarEmail =
            popUpView.findViewById<MaterialButton>(R.id.btnCambiarEmailPopup)
        val nombreFamilia = popUpView.findViewById<TextView>(R.id.nombreFamiliaPopup)
        val codigoFamilia = popUpView.findViewById<TextView>(R.id.codigoFamiliaPopup)
        val passFamilia = popUpView.findViewById<TextView>(R.id.passFamiliaPopup)
        val btnSalirFamilia =
            popUpView.findViewById<MaterialButton>(R.id.btnSalirFamiliaPopup)
        val btnCerrarSesion =
            popUpView.findViewById<MaterialButton>(R.id.btnCerrarSesionPopup)

        val familia = viewModel.getFamilia().value
        val userName = ListaAppApplication.prefsHelper.getUserName()
        val userEmail = ListaAppApplication.prefsHelper.getUserEmail()

        if (familia != null) {
            nombreUsuario.text = "Nombre del usuario: $userName"
            email.text = "Email: $userEmail"
            nombreFamilia.text = "Familia: ${familia.nombre}"
            codigoFamilia.text = "Código: ${familia.id}"
            passFamilia.text = "Contraseña: ${familia.password}"
        }

        btnCambiarEmail.setOnClickListener {
            if (inputEmail != null) {
                if (!HelperClass.isEmailValid(inputEmail.text.toString().trim())) {
                    tfNewEmail.error = "Email invalido"
                } else {
                    btnCambiarEmail.icon = HelperClass.getCircularProgress(requireContext())
                    btnCambiarEmail.isClickable = false
                    authViewModel.changeEmail(
                        inputEmail.text.toString(),
                    )
                }
            }
        }

        btnSalirFamilia.setOnClickListener {
            authViewModel.borrarseDeFamilia()
        }

        btnCerrarSesion.setOnClickListener {
            authViewModel.logout()
        }

        authViewModel.authState.observe(this) {
            if (it.successMessage.isNotBlank()) {
                showToast(
                    requireContext(),
                    it.successMessage
                )
                authViewModel.logout()
            }
            if (it.errorMessage.isNotBlank()) showToast(
                requireContext(),
                it.errorMessage
            )
        }

        inputEmail?.doAfterTextChanged {
            tfNewEmail.error = null
            if (!HelperClass.isEmailValid(inputEmail.text.toString().trim())) {
                tfNewEmail.helperText = "Ingrese un email válido"
            } else tfNewEmail.helperText = null
        }

        popupBuilder.setView(popUpView)
        popup = popupBuilder.create()
        popup.show()

        btnCerrar.setOnClickListener {
            popup.dismiss()
        }
    }

    private fun cargarProductos(){
        productosAlacena?.clear()
        productosAlacena?.addAll(viewModel.getProductosByIdLista(viewModel.getIdAlacenaVirtual()).toMutableList())
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        val rvAdapter = binding.alacenaProductos.adapter
        viewModel.getFamilia().observe(this, Observer {
            cargarProductos()
            rvAdapter?.notifyDataSetChanged()
        })
    }

    private fun clickSumaYResta(producto: ItemLista, cantidad: Int) {
        viewModel.actualizarProductoEnListaById(
            viewModel.getIdAlacenaVirtual(),
            producto.producto.id,
            cantidad
        )
        productosAlacena?.indexOf(producto)?.let {
            binding.alacenaProductos.adapter?.notifyItemChanged(
                it,"Payload"
            )
        }
        if(cantidad>0){
            showToast(requireContext(),"Se ha agregado el producto")
        }else{
            showToast(requireContext(),"Se ha quitado el producto")
        }
            }
}