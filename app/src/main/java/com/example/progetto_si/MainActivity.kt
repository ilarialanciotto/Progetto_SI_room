package com.example.progetto_si

import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Acquisti.Acquisti
import com.example.progetto_si.Acquisti.AcquistiViewModel
import com.example.progetto_si.Admin.Admin
import com.example.progetto_si.Admin.AdminViewModel
import com.example.progetto_si.Cliente.Cliente
import com.example.progetto_si.Cliente.ClienteViewModel
import com.example.progetto_si.Login.Login
import com.example.progetto_si.Note.Note
import com.example.progetto_si.Note.NoteViewModel
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoViewModel
import com.example.progetto_si.Sviluppatore.Sviluppatore
import com.example.progetto_si.Sviluppatore.SviluppatoreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val Web : WebView = findViewById(R.id.webVW)
        Web.webViewClient = WebViewClient()

        //sito creato da noi
        Web.loadUrl("https://cybersicuri.certfin.it/")

        var packWM = PacchettoViewModel(application)
        lifecycleScope.launch {
            packWM.getAllPacchetti { stringList->
                if(stringList.isEmpty())
                    initDB(packWM)
            }
        }
    }

    private fun initDB(packWM : PacchettoViewModel){

        var clWM = ClienteViewModel(application)
        var acquistoWM = AcquistiViewModel(application)
        var noteWM = NoteViewModel(application)
        var admVM = AdminViewModel(application)
        var sviWM = SviluppatoreViewModel(application)

        var svi = Sviluppatore(
            nome = "Marco",
            cognome = "Verdi",
            email = "marco.verdi@example.com",
            password = "developer123",
            livello = "senior",
            progetti = "Progetto A, Progetto B",
            tipo = "sviluppatore"
        )

        var adm = Admin(
            nome = "Admin",
            cognome = "SuperUser",
            email = "admin@example.com",
            password = "admin123",
            ruolo = "admin"
        )

        var cli2 = Cliente(
            nome = "Luca",
            cognome = "Bianchi",
            email = "luca.bianchi@example.com",
            password = "securePass",
            telefono = "0987654321",
            azienda = "Tech Corp",
            tipo = "cliente"
        )

        var cli = Cliente(
            nome = "Mario",
            cognome = "Rossi",
            email = "mario.rossi@example.com",
            password = "password123",
            telefono = "1234567890",
            azienda = "Example Corp",
            tipo = "cliente"
        )

        var not = Note(
            data = "2025-1-1",
            username = "lalla",
            nota = "nota di prova",
            pacchetto = 1
        )

        var acquisto = Acquisti(
            cliente = 1,
            pacchetto = 1,
            quantità = 5
        )

        sviWM.insert(svi)
        admVM.insert(adm)
        clWM.insert(cli2)
        clWM.insert(cli)
        noteWM.insert(not)
        acquistoWM.insert(acquisto)

        val pacchettoBase = Pacchetto(
            nome = "Pacchetto Base",
            dettagli = "Un pacchetto base per piccole aziende.",
            prezzo = 299.99,
            componenteHardware = "Router di base",
            componenteSoftware = "Antivirus standard"
        )

        packWM.insert(pacchettoBase)

        val pacchettoAvanzato = Pacchetto(
            nome = "Pacchetto Avanzato",
            dettagli = "Un pacchetto avanzato per medie imprese.",
            prezzo = 599.99,
            componenteHardware = "Router avanzato + Firewall",
            componenteSoftware = "Antivirus avanzato + Gestione della rete"
        )

        packWM.insert(pacchettoAvanzato)

        val pacchettoPremium = Pacchetto(
            nome = "Pacchetto Premium",
            dettagli = "Un pacchetto premium per grandi aziende.",
            prezzo = 1299.99,
            componenteHardware = "Server dedicato + Router di alta gamma",
            componenteSoftware = "Suite completa di sicurezza informatica"
        )

        packWM.insert(pacchettoPremium)

        val pacchettoStartup = Pacchetto(
            nome = "Pacchetto Startup",
            dettagli = "Un pacchetto pensato per startup e nuove attività.",
            prezzo = 399.99,
            componenteHardware = "Router base",
            componenteSoftware = "Gestione della rete + Antivirus"
        )

        packWM.insert(pacchettoStartup)

        val pacchettoEnterprise = Pacchetto(
            nome = "Pacchetto Enterprise",
            dettagli = "Un pacchetto su misura per grandi imprese.",
            prezzo = 1999.99,
            componenteHardware = "Server aziendale + Firewall dedicato",
            componenteSoftware = "Sicurezza totale + Gestione centralizzata"
        )

        packWM.insert(pacchettoEnterprise)

        val pacchettoHomeOffice = Pacchetto(
            nome = "Pacchetto Home Office",
            dettagli = "Un pacchetto per chi lavora da casa.",
            prezzo = 199.99,
            componenteHardware = "Modem router",
            componenteSoftware = "Antivirus base + Protezione dati"
        )

        packWM.insert(pacchettoHomeOffice)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.main_menu),menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_login -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_register -> {
                val intent = Intent(this, Register::class.java)
                startActivity(intent)
                return true
            }
            R.id.mappa -> {
                val intent = Intent(this, mappaView::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}