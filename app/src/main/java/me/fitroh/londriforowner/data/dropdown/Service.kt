package me.fitroh.londriforowner.data.dropdown

data class Service(val status: String)

private val service = arrayOf(
    "Menunggu Diterima",
    "Diterima",
    "Menunggu Diambil",
    "Sedang Diproses",
    "Sedang Dikirim",
    "Selesai"
)

object ServiceListProvider {
    val list: ArrayList<Service> by lazy {
        ArrayList<Service>().apply {
            for (status in service) {
                add(Service(status))
            }
        }
    }
}
