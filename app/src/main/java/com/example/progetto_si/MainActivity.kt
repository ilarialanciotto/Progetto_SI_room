package com.example.progetto_si

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.lifecycleScope
import com.example.progetto_si.Acquisti.Acquisti
import com.example.progetto_si.Acquisti.AcquistiViewModel
import com.example.progetto_si.Admin.Admin
import com.example.progetto_si.Admin.AdminViewModel
import com.example.progetto_si.Cliente.Room.Cliente
import com.example.progetto_si.Cliente.Room.ClienteViewModel
import com.example.progetto_si.Login.Login
import com.example.progetto_si.Note.Note
import com.example.progetto_si.Note.NoteViewModel
import com.example.progetto_si.Pacchetto.Pacchetto
import com.example.progetto_si.Pacchetto.PacchettoViewModel
import com.example.progetto_si.Sviluppatore.Sviluppatore
import com.example.progetto_si.Sviluppatore.SviluppatoreViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        val Web : WebView = findViewById(R.id.webVW)
        Web.webViewClient = WebViewClient()
        Web.settings.javaScriptEnabled = true
        //sito creato da noi
        Web.loadUrl("https://cybersicuri.certfin.it/")

        // Popola il database
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
        var admWM = AdminViewModel(application)
        var sviWM = SviluppatoreViewModel(application)

        val admin = Admin(
            nome = "Admin",
            cognome = "SuperUser",
            email = "admin@example.com",
            password = "admin123",
            ruolo = "admin"
        )

        val svi = Sviluppatore(
            nome = "Marco",
            cognome = "Verdi",
            email = "marco.verdi@example.com",
            password = "developer123",
            livello = "senior",
            progetti = "Progetto A, Progetto B",
            tipo = "sviluppatore"
        )

        val svi1 = Sviluppatore(
            nome = "Luigi",
            cognome = "Verdi",
            email = "luigi.verdi@example.com",
            password = "developer123",
            livello = "senior",
            progetti = "Progetto A, Progetto B",
            tipo = "Analista"
        )

        val svi2 = Sviluppatore(
            nome = "Francesco",
            cognome = "Verdi",
            email = "francesco.verdi@example.com",
            password = "developer123",
            livello = "junior",
            progetti = "Progetto A, Progetto B",
            tipo = "sviluppatore"
        )

        val svi3 = Sviluppatore(
            nome = "Chiara",
            cognome = "Verdi",
            email = "chira.verdi@example.com",
            password = "developer123",
            livello = "senior",
            progetti = "Progetto A, Progetto B",
            tipo = "Responsabile consegne digitali"
        )

        val cli = Cliente(
            nome = "ilaria",
            cognome = "lanciotto",
            email = "lalla@example.com",
            password = "securePass",
            telefono = "0987654321",
            azienda = "Secure Tech",
            tipo = "cliente"
        )

        val cli2 = Cliente(
            nome = "Mario",
            cognome = "Rossi",
            email = "mario.rossi@example.com",
            password = "password123",
            telefono = "1234567890",
            azienda = "Example Corp",
            tipo = "cliente"
        )

        val cli3 = Cliente(
            nome = "Luca",
            cognome = "Bianchi",
            email = "luca.bianchi@example.com",
            password = "securePass",
            telefono = "0987654321",
            azienda = "Tech Corp",
            tipo = "cliente"
        )

        var not = Note(
            data = "2025-1-1",
            email = "lalla@example.com",
            nota = "nota di prova",
            pacchetto = 1
        )

        var acquisto = Acquisti(
            cliente = 1,
            pacchetto = 1,
        )

        sviWM.insert(svi)
        clWM.insert(cli)
        clWM.insert(cli2)
        clWM.insert(cli3)
        noteWM.insert(not)
        admWM.insert(admin)
        sviWM.insert(svi1)
        sviWM.insert(svi2)
        sviWM.insert(svi3)
        acquistoWM.insert(acquisto)

        val pacchettoBase = Pacchetto(
            nome = "Pacchetto Base",
            descrizione = "Un pacchetto base per piccole aziende.",
            prezzo = 299.99,
            durata = "10g",
            componenteHardware = "Router di base",
            componenteSoftware = "Antivirus standard"
        )

        packWM.insert(pacchettoBase)

        val pacchettoAvanzato = Pacchetto(
            nome = "Pacchetto Avanzato",
            descrizione = "Un pacchetto avanzato per medie imprese.",
            prezzo = 599.99,
            durata = "10g",
            componenteHardware = "Router avanzato + Firewall",
            componenteSoftware = "Antivirus avanzato + Gestione della rete"
        )

        packWM.insert(pacchettoAvanzato)

        val pacchettoPremium = Pacchetto(
            nome = "Pacchetto Premium",
            descrizione = "Un pacchetto premium per grandi aziende.",
            prezzo = 1299.99,
            durata = "10g",
            componenteHardware = "Server dedicato + Router di alta gamma",
            componenteSoftware = "Suite completa di sicurezza informatica"
        )

        packWM.insert(pacchettoPremium)

        val pacchettoStartup = Pacchetto(
            nome = "Pacchetto Startup",
            descrizione = "Un pacchetto pensato per startup e nuove attività.",
            prezzo = 399.99,
            durata = "10g",
            componenteHardware = "Router base",
            componenteSoftware = "Gestione della rete + Antivirus"
        )

        packWM.insert(pacchettoStartup)

        val pacchettoEnterprise = Pacchetto(
            nome = "Pacchetto Enterprise",
            descrizione = "Un pacchetto su misura per grandi imprese.",
            prezzo = 1999.99,
            durata = "10g",
            componenteHardware = "Server aziendale + Firewall dedicato",
            componenteSoftware = "Sicurezza totale + Gestione centralizzata"
        )

        packWM.insert(pacchettoEnterprise)

        val pacchettoHomeOffice = Pacchetto(
            nome = "Pacchetto Home Office",
            descrizione = "Un pacchetto per chi lavora da casa.",
            prezzo = 199.99,
            durata = "10g",
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