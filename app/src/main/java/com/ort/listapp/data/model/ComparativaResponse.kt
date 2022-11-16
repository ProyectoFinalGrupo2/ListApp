package com.ort.listapp.data.model

import com.google.gson.annotations.SerializedName

data class ComparativaResponse(
    val status: Long,
    val totalProductos: Long,
    val sucursales: List<Sucursal>,
    val totalSucursales: Long
)

data class Sucursal(
    val sucursalTipo: String,
    val direccion: String,
    val sumaPrecioListaTotal: Double,
    val sumaPrecioListaDisponible: Long,
    val provincia: String,
    val totalSugeridos: Long,
    val productos: List<Producto>,

    @SerializedName("banderaId")
    val banderaID: Long,

    val localidad: String,
    val banderaDescripcion: String,
    val actualizadoHoy: Boolean,
    val totalProductos: Long,
    val lat: String,
    val comercioRazonSocial: String,
    val lng: String,
    val sucursalNombre: String,

    @SerializedName("comercioId")
    val comercioID: Long,

    val id: String,

    @SerializedName("sucursalId")
    val sucursalID: String
)

data class Producto(
    val marca: String,
    val promo1: Promo,
    val promo2: Promo,

    @SerializedName("id_string")
    val idString: String,

    @SerializedName("unidad_venta")
    val unidadVenta: String,

    val presentacion: String,

    @SerializedName("precio_unitario_con_iva")
    val precioUnitarioConIva: Any,

    val id: String,
    val precioLista: Double,

    @SerializedName("precio_unitario_sin_iva")
    val precioUnitarioSinIva: Any,

    val nombre: String
)

data class Promo(
    val descripcion: String,

    @SerializedName("precio_unitario_sin_iva")
    val precioUnitarioSinIva: Any,

    @SerializedName("precio_unitario_con_iva")
    val precioUnitarioConIva: Any,

    val precio: Any
)

fun ComparativaResponse.toPreciosProductos(): Map<String, Double> {
    val map = mutableMapOf<String, Double>()
    this.sucursales.forEach { sucursal ->
        sucursal.productos.forEach { producto ->
            val id = producto.id
            val precioLista = producto.precioLista
            if (precioLista > 0) {
                if (map.containsKey(id) && map[id]!! > precioLista) map.replace(id, precioLista)
                else map[id] = precioLista
            }
        }
    }
    return map
}

