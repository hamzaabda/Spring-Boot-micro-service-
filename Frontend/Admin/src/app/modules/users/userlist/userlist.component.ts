import { Component, OnInit, ViewChild } from '@angular/core';
import { UsersService } from '../users.service';
import Swal from "sweetalert2"
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../../auth/service/auth.service';

@Component({
  selector: 'app-userlist',
  templateUrl: './userlist.component.html',
  styleUrls: ['./userlist.component.scss']
})
export class UserlistComponent implements OnInit {
  // bread crumb items
  breadCrumbItems: Array<{}>;
  formData: FormGroup;
  usersList: [];
  userRoles:[];
  roleid: any[] = [];
  selectedUser:any;
  @ViewChild('content') content: any;
  constructor(private userService:UsersService,
    private modalService:NgbModal,
    private formBuilder: FormBuilder,
    private authservice:AuthService
    ) { }

  ngOnInit() {



    this.authservice.getroles().subscribe((data: any) => {
      this.roleid = data.map((role: any) => ({
        id: role.id,
        name: role.nameRole,
      }));
    });

    this.formData = this.formBuilder.group({
      username: ['', [Validators.required]],
      email: new FormControl('', [Validators.required]),
      nom: ['', [Validators.required]],
      prenom: ['', [Validators.required]],
      isEnabled :['', [Validators.required]],
      profileimageurl :['', [Validators.required]],
      birthdate:['', [Validators.required]],
      phone :['', [Validators.required]],
      adress:['', [Validators.required]],
      roleid: ['', Validators.required]
    });

    this.userService.getalluseradmin().subscribe(
      (data)=>
      {
        this.usersList = data;
        console.log("users"+this.usersList)
        data.forEach(element => {
          element.roles.forEach(role=>{

            this.userRoles = role
              console.log("Role: "+role.nameRole)

            })
        })
      }
      ,error=> console.log(error)
      
    );

    this.breadCrumbItems = [{ label: 'Admin' }, { label: 'Users List', active: true }];
  }

  deleteUser(userId: number) {
    // Afficher une boîte de dialogue de confirmation
    Swal.fire({
      title: 'Êtes-vous sûr de vouloir supprimer cet utilisateur ?',
      text: 'Cette action est irréversible.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        // Appeler le service pour supprimer l'utilisateur
        this.userService.deleteuser(userId).subscribe(
          (data) => {
            // Traitement après la suppression réussie
            // Vous pouvez ajouter ici une notification ou une redirection, par exemple
            Swal.fire(
              'Utilisateur supprimé !',
              'L\'utilisateur a été supprimé avec succès.',
              'success'
            );
          },
          (error) => {
            // Gérer les erreurs de suppression, par exemple afficher un message d'erreur
            Swal.fire(
              'Erreur',
              'Une erreur s\'est produite lors de la suppression de l\'utilisateur.',
              'error'
            );
          }
        );
      } else {
        // L'utilisateur a annulé la suppression, aucune action nécessaire
      }
    });
  }
  
  openModal(idUser: any) {
    // Ouvrir la modal
    this.modalService.open(this.content, { windowClass: 'modal-holder' });
  
    // Utilisez idUser pour récupérer les données de l'utilisateur par ID
    this.userService.fetchUserById(idUser).subscribe(
      (data: any) => { 
        
        console.log(data)
        
        // Utilisation d'un type générique (any) pour ne pas spécifier la structure
        // Mettre à jour la variable selectedUser avec les données de l'utilisateur sélectionné
        this.selectedUser = data;
        
        // Remplir les champs du formulaire avec les données de l'utilisateur sélectionné
        this.formData.patchValue({
          username: data?.username,
          email: data?.email,
          nom: data?.nom,
          prenom: data?.prenom,
          isEnabled: data?.isEnabled,
          profileimageurl: data?.profileimageurl,
          birthdate: data?.birthdate,
          phone: data?.phone,
          adress: data?.adress,
          roleid: data?.roles.map((role: any) => role.nameRole).join(', '),
        });
      },
      (error) => {
        console.log(error);
      }
    );
  }


// Créez une fonction pour enregistrer les modifications et les envoyer à l'API d'édition
saveUser() {
  // Obtenez les données modifiées à partir du formulaire
  const modifiedUserData = this.formData.value;
  const userId = this.selectedUser.id;
 
  // Obtenez les rôles modifiés à partir de la chaîne
  const modifiedRoles = modifiedUserData.roleid.split(',').map(role => role.trim());

  // Mettez à jour les rôles de l'utilisateur dans les données modifiées
  modifiedUserData.roles = modifiedRoles;

  // Appelez le service pour mettre à jour l'utilisateur
  this.userService.editUser(modifiedUserData, userId).subscribe(
    (response: any) => {
      // La mise à jour de l'utilisateur a réussi
      
      // Mettez à jour la liste des utilisateurs sans rechargement
      this.userService.getalluseradmin().subscribe(users => {
        this.usersList = users;
      });

      // Fermez la boîte de dialogue de mise à jour
      this.modalService.dismissAll();
    },
    (error) => {
      // En cas d'erreur lors de la mise à jour de l'utilisateur
      console.error('Erreur lors de la mise à jour de l\'utilisateur :', error);
    }
  );
}




  









  getRoleDisplayName(roleName: string): string {
    switch (roleName) {
      case 'ROLE_PARTICIPANT':
        return 'Participant';
      case 'ROLE_ORGANISATEUR':
        return 'Organisateur';
      case 'ROLE_PARTENAIRE':
        return 'Partenaire';
      default:
        return roleName; // Use the original roleName if not found in the mapping
    }
  }
}
