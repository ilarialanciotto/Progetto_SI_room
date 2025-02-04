package com.example.progetto_si

import android.content.Intent
import android.content.res.Configuration
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
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        loadLocale()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        val Web : WebView = findViewById(R.id.webVW)
        Web.webViewClient = WebViewClient()
        Web.settings.javaScriptEnabled = true
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
            tipo = "analista"
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
            progetti = getString(R.string.progetto_a_progetto_b),
            tipo = "responsabile consegne digitali"
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
            nota = getString(R.string.nota_di_prova),
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
            nome = getString(R.string.pacchetto_base),
            descrizione = getString(R.string.un_pacchetto_base_per_piccole_aziende),
            prezzo = 299.99,
            durata = "10g",
            componenteHardware = getString(R.string.router_di_base),
            componenteSoftware = getString(R.string.antivirus_standard)
        )

        packWM.insert(pacchettoBase)

        val pacchettoAvanzato = Pacchetto(
            nome = getString(R.string.pacchetto_avanzato),
            descrizione = getString(R.string.un_pacchetto_avanzato_per_medie_imprese),
            prezzo = 599.99,
            durata = "10g",
            componenteHardware = getString(R.string.router_avanzato_firewall),
            componenteSoftware = getString(R.string.antivirus_avanzato_gestione_della_rete)
        )

        packWM.insert(pacchettoAvanzato)

        val pacchettoPremium = Pacchetto(
            nome = getString(R.string.pacchetto_premium),
            descrizione = getString(R.string.un_pacchetto_premium_per_grandi_aziende),
            prezzo = 1299.99,
            durata = "10g",
            componenteHardware = getString(R.string.server_dedicato_router_di_alta_gamma),
            componenteSoftware = getString(R.string.suite_completa_di_sicurezza_informatica)
        )

        packWM.insert(pacchettoPremium)

        val pacchettoStartup = Pacchetto(
            nome = getString(R.string.pacchetto_startup),
            descrizione = getString(R.string.un_pacchetto_pensato_per_startup_e_nuove_attivit),
            prezzo = 399.99,
            durata = "10g",
            componenteHardware = getString(R.string.router_base),
            componenteSoftware = getString(R.string.gestione_della_rete_antivirus)
        )

        packWM.insert(pacchettoStartup)

        val pacchettoEnterprise = Pacchetto(
            nome = getString(R.string.pacchetto_enterprise),
            descrizione = getString(R.string.un_pacchetto_su_misura_per_grandi_imprese),
            prezzo = 1999.99,
            durata = "10g",
            componenteHardware = getString(R.string.server_aziendale_firewall_dedicato),
            componenteSoftware = getString(R.string.sicurezza_totale_gestione_centralizzata)
        )

        packWM.insert(pacchettoEnterprise)

        val pacchettoHomeOffice = Pacchetto(
            nome = getString(R.string.pacchetto_home_office),
            descrizione = getString(R.string.un_pacchetto_per_chi_lavora_da_casa),
            prezzo = 199.99,
            durata = "10g",
            componenteHardware = getString(R.string.modem_router),
            componenteSoftware = getString(R.string.antivirus_base_protezione_dati)
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
            R.id.menu_language -> {
                showLanguageDialog()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("Italiano", "English", "Français")

        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Seleziona Lingua")
            .setItems(languages) { _, which ->
                when (which) {
                    0 -> setLocale("it") // Italiano
                    1 -> setLocale("en") // Inglese
                    2 -> setLocale("fr") // Francese
                }
            }
        builder.create().show()
    }

    private fun setLocale(langCode: String) {
        val locale = Locale(langCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        // Salva la lingua nelle SharedPreferences
        val prefs = getSharedPreferences("Settings", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("App_Lang", langCode)
        editor.apply()

        // Ricarica l'activity per applicare la lingua
        val intent = intent
        finish()
        startActivity(intent)
    }


    private fun loadLocale() {
        val prefs = getSharedPreferences("Settings", MODE_PRIVATE)
        val language = prefs.getString("App_Lang", "it") // Default italiano

        val locale = Locale(language!!)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }



}