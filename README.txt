Toutes les fonctionnalités des TP ont (normalement) été implémentées.

Fonctionnalités supplémentaires:

1. Possibilité de passer d'un jeu à un autre quand on est sur les détails d'un jeu. On peut passer au
précédent ou au suivant et la logique est bien circulaire.

2. Changement de l'icône de l'app.

3. La mise en favoris des jeux est persistante.

4. Ajout d'un bouton flottant en bas à droite de la page principale pour remonter en haut de celle-ci.

5. Quand on reste appuyé sur un jeu parmi la liste de jeux (page principale), une popup apparait et nous
propose via un bouton de supprimer le jeu de la liste (cas géré si on supprime tous les jeux).

6. Lorsqu'on remonte plus haut que la page, on déclenche un refresh qui affiche une popup qui nous propose
via un boutton de réafficher les jeux qui ont été supprimé.

7. Ajout d'un slider dans les détails du jeu pour donner une note sur 10 à celui-ci. La note est affichée
dans la liste des jeux à côté du logo des favoris et est persistante.

8. Ajout d'un bouton en haut à droite de la page principale pour accéder à une page "profil utilisateur"
qui comprend : Un logo // un nom/pseudo modifiable et persistant // un affichage des jeux mis en favoris
(si on l'enlève des favoris depuis le profil, il disparait de cette liste)

9. Ajout du système d'authentification au lancement de l'application. Si aucun système n'est détecté
(normalement pas le cas car il y a en généralau moins un MDP), l'application est lancée quand même
pour ne pas bloquer certains potentiels tests avec de tels appareils.