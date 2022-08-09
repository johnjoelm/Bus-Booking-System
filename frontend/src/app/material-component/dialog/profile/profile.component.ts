import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  dataSource:any;
  username:any;
  fullName:any;
  phone:any;
  email:any;

  constructor(@Inject(MAT_DIALOG_DATA) public dialogData:any,
  public dialogRef: MatDialogRef<ProfileComponent>) { }

  ngOnInit() {
    this.username = localStorage.getItem('username');
    this.fullName = localStorage.getItem('fullName');
    this.phone = localStorage.getItem('phone');
    this.email = localStorage.getItem('email');  }

}
