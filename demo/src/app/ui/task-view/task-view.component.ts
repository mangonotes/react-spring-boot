import { Component, OnInit , Input} from '@angular/core';
import { Task } from 'src/app/backend/task.model';

@Component({
  selector: 'app-task-view',
  templateUrl: './task-view.component.html',
  styleUrls: ['./task-view.component.css']
})
export class TaskViewComponent implements OnInit {
  @Input()
  task!: Task;

  constructor() { }

  ngOnInit(): void {
  }

}
