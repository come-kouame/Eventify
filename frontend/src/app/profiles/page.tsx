import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected";

export default function Home() {
    return (
        <div className="min-h-screen font-sans">
            {/* Header */}
            <NavBarNotConnected/>

            {/* Title */}
            <h1 className="text-center text-5xl font-medium my-8">
                Gestion des comptes
            </h1>

            <div className="flex px-6 gap-6">
                {/* Sidebar */}
                <div className="w-1/4 flex flex-col gap-6">
                    <button className="bg-gray-300 text-xl py-4">
                        Créer un Compte
                    </button>

                    <div className="bg-gray-300 p-6">
                        <h2 className="text-4xl mb-4">Filtres</h2>
                        <div className="text-xl space-y-2">
                            <label className="flex items-center gap-2">
                                <input type="checkbox" className="w-5 h-5" />
                                Administrateur
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox" className="w-5 h-5" />
                                Organisateur
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox" className="w-5 h-5" />
                                Participant
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox" className="w-5 h-5" />
                                Supprimé
                            </label>
                        </div>
                    </div>
                </div>

                {/* Table */}
                <div className="flex-1 bg-gray-300 p-4">
                    <table className="w-full border-collapse">
                        <thead>
                            <tr className="bg-gray-400 text-left">
                                <th className="p-2">Nom</th>
                                <th className="p-2">Prénom</th>
                                <th className="p-2">E-mail</th>
                                <th className="p-2">Statut</th>
                                <th className="p-2">Modifier</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr className="border-t">
                                <td className="p-2">Morel</td>
                                <td className="p-2">Louis</td>
                                <td className="p-2">lm38550@gmail.com</td>
                                <td className="p-2">Administrateur</td>
                                <td className="p-2">
                                    <a href="/profiles/x" className="bg-blue-500 text-white px-4 py-1 rounded">
                                        Modifier
                                    </a>
                                </td>
                            </tr>

                            {/* Empty rows for layout */}
                            {Array.from({ length: 12 }).map((_, i) => (
                                <tr key={i} className="border-t">
                                    <td className="p-2">&nbsp;</td>
                                    <td className="p-2"></td>
                                    <td className="p-2"></td>
                                    <td className="p-2"></td>
                                    <td className="p-2"></td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}
