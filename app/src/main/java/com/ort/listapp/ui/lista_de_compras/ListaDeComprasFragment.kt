package com.ort.listapp.ui.lista_de_compras

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.ort.listapp.ListaAppApplication
import com.ort.listapp.R
import com.ort.listapp.databinding.FragmentListaDeComprasBinding
import com.ort.listapp.domain.model.ItemLista
import com.ort.listapp.domain.model.TipoLista
import com.ort.listapp.ui.FamilyViewModel
import com.ort.listapp.ui.adapters.ProductoListadoAdapter
import com.ort.listapp.ui.adapters.RealizarCompraAdapter
import com.ort.listapp.ui.auth.AuthViewModel
import com.ort.listapp.utils.HelperClass
import com.ort.listapp.utils.HelperClass.showToast

@Suppress("DEPRECATION")
@SuppressLint("SetTextI18n")
class ListaDeComprasFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private val viewModel: FamilyViewModel by activityViewModels()
    private lateinit var binding: FragmentListaDeComprasBinding

    private lateinit var rvListaCompra: RecyclerView
    private lateinit var rvListaRC: RecyclerView

    private lateinit var popup: AlertDialog
    private lateinit var popupBuilder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            AlertDialog.Builder(context)
                .setTitle("Salir")
                .setMessage("Desea cerrar sesión?")
                .setPositiveButton("SI") { dialog, _ ->
                    authViewModel.logout()
                    showToast(requireContext(), "Adiós...")
                    dialog.cancel()
                }
                .setNegativeButton("NO", null)
                .show()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListaDeComprasBinding.inflate(inflater, container, false)

        // This is the appropriate place to set up the initial state of your view, to start observing
        // LiveData instances whose callbacks update the fragment's view, and to set up adapters on
        // any RecyclerView or ViewPager2 instances in your fragment's view.
        // from: https://developer.android.com/guide/fragments/lifecycle
        initRecyclersViews()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun initRecyclersViews() {
        rvListaCompra = binding.rvListaCompra
        rvListaCompra.setHasFixedSize(true)
        rvListaCompra.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        rvListaRC = binding.rvListaRC
        rvListaRC.setHasFixedSize(true)
        rvListaRC.layoutManager = LinearLayoutManager(requireContext())
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
                direction =
                    ListaDeComprasFragmentDirections.actionListaDeComprasFragmentToUserConfigFragment()

                popupBuilder = AlertDialog.Builder(context)
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
            else -> super.onOptionsItemSelected(item)
        }
//        direction?.let {
//            view?.findNavController()?.navigate(direction)
//        }
        return true
    }

    override fun onStart() {
        super.onStart()

        val btnRealizarCompra = binding.btnRealizarCompra
        val btnEditarLista = binding.btnEditarLista
        val btnComprasFavoritas = binding.btnComprasFavoritas
        val btnAgregarListaFav = binding.btnAgregarListaFav
        val btnHistorial = binding.btnCrearLista
        val txtPrecioTotalLista = binding.txtPrecioTotalLista

        viewModel.getFamilia().observe(this) {
            val total = viewModel.precioTotalListaById(viewModel.getIdListaDeComprasActual())
                .toString()
            txtPrecioTotalLista.text = "Precio total: $$total"
            val productos = viewModel.getProductosByIdLista(viewModel.getIdListaDeComprasActual())
            rvListaCompra.adapter = ProductoListadoAdapter(
                productos, requireContext(),
                { removerProducto(it) },
                { clickSumaYResta(it, 1) },
                { clickSumaYResta(it, -1) }
            )

            viewModel.cargarChecklist()

            rvListaRC.adapter = RealizarCompraAdapter(viewModel.getProductosChecklist()) {
                clickChecklist(it)
            }
        }

        if(viewModel.estaEnRealizarCompra()){
            visibilidadListaCompras(View.INVISIBLE)
            visibilidadChecklist(View.VISIBLE)
        }

        btnRealizarCompra.setOnClickListener {
            realizarCompra()
        }

        btnEditarLista.setOnClickListener {
            editarLista()
        }

        binding.btnConfirmarCompra.setOnClickListener {
            if(viewModel.hayProductosCheckeados()){
                viewModel.realizarCompra()
                editarLista()
            }else{
                showToast(requireContext(),"No se seleccionó ningún producto")
            }
        }

        btnComprasFavoritas.setOnClickListener {
            val action =
                ListaDeComprasFragmentDirections.actionListaDeComprasFragmentToComprasFavoritasFragment()
            view?.findNavController()?.navigate(action)
//            this.findNavController().navigate(action)
        }

        btnHistorial.setOnClickListener {
            val action =
                ListaDeComprasFragmentDirections.actionListaDeComprasFragmentToHistorial()
            view?.findNavController()?.navigate(action)
        }

        btnAgregarListaFav.setOnClickListener {
            popupBuilder = AlertDialog.Builder(context)
            val popupView = layoutInflater.inflate(R.layout.popup_crear_compra_fav, null)
            val nombreLista = popupView.findViewById<EditText>(R.id.txt_nombre_compra_fav)
            val btnCrear = popupView.findViewById<Button>(R.id.btn_crear_lista)
            val btnCerrar = popupView.findViewById<ImageView>(R.id.btn_cerrar_popup)

            btnCerrar.setOnClickListener {
                popup.dismiss()
            }

            btnCrear.setOnClickListener {
                val nombre = nombreLista.text.toString()
                if (nombre.isNotEmpty()) {
                    viewModel.crearLista(nombre,TipoLista.LISTA_FAVORITA)
                    popup.dismiss()
                } else {
                    nombreLista.error = "El nombre de la lista no puede estar vacío."
                }
            }

            popupBuilder.setView(popupView)
            popup = popupBuilder.create()
            popup.show()
        }
    }

    private fun removerProducto(itemLista: ItemLista) {
        viewModel.removerProductoDeListaById(
            viewModel.getIdListaDeComprasActual(),
            itemLista.producto.id
        )
    }

    private fun clickSumaYResta(producto: ItemLista, cantidad: Int) {
        viewModel.actualizarProductoEnListaById(
            viewModel.getIdListaDeComprasActual(),
            producto.producto.id,
            cantidad
        )
    }

    private fun realizarCompra() {
        if(viewModel.getProductosByIdLista(viewModel.getIdListaDeComprasActual()).isNotEmpty()){
            //oculto los componentes de la lista de compras
            visibilidadListaCompras(View.GONE)

            //muestro los componentes de realizar compra con la checklist
            visibilidadChecklist(View.VISIBLE)

            viewModel.setEstaEnRealizarCompra(true)

            binding.precioTotalCompra.text =
                "Precio total: $" + viewModel.precioTotalListaById(viewModel.getIdListaDeComprasActual())
                    .toString()

        }else{
            showToast(requireContext(),"No hay productos en la lista")
        }
    }

    private fun clickChecklist(idProducto: String) {
        viewModel.clickChecklistProducto(idProducto)
    }

    private fun editarLista() {
        //oculto los componentes de realizar compra con la checklist
        visibilidadChecklist(View.INVISIBLE)

        //muestro los componentes de la lista de compras
        visibilidadListaCompras(View.VISIBLE)

        viewModel.setEstaEnRealizarCompra(false)
    }

    private fun visibilidadChecklist(visibility: Int) {
        binding.rvListaRC.visibility = visibility
        binding.btnConfirmarCompra.visibility = visibility
        binding.btnEditarLista.visibility = visibility
        binding.precioTotalCompra.visibility = visibility
        binding.txtConfirmarCompra.visibility = visibility
    }

    private fun visibilidadListaCompras(visibility: Int) {
        binding.btnComprasFavoritas.visibility = visibility
        binding.btnAgregarListaFav.visibility = visibility
        binding.btnRealizarCompra.visibility = visibility
        binding.btnCrearLista.visibility = visibility
        binding.rvListaCompra.visibility = visibility
        binding.txtPrecioTotalLista.visibility = visibility
    }
}

