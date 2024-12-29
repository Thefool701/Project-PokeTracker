#include <malloc.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define LEN 128

void clear_terminal() {
#ifdef _WIN32
  // For Windows
  system("cls");
#else
  // For Unix/Linux/Mac
  system("clear");
#endif
}

struct Routes_t {
  char route_name[LEN];
  char pokemon[LEN];
  char typing[LEN];
  bool status;
  struct Routes_t *next;
};
struct Tracker_t {
  char route_name[LEN];
  char pokemon[LEN];
  char typing[LEN];
  bool status;
  struct Tracker_T *next;
};
struct Routes_t *create_node(char data[LEN]) {
  struct Routes_t *new_node =
      (struct Routes_t *)malloc(sizeof(struct Routes_t));
  strcpy(new_node->route_name, data);
  new_node->next = NULL;
  return new_node;
}
void print_list(struct Routes_t *head) {
  struct Routes_t *temp = head;
  while (temp != NULL) {
    printf("%s -> ", temp->route_name);
    temp = temp->next;
  }
  printf("NULL\n");
}

void print_to_file(struct Routes_t *head, FILE *outfile) {
  struct Routes_t *temp = head;
  fprintf(outfile,
          "Route          Pokemon Caught          Typing          Status\n");
  while (temp != NULL) {
    fprintf(outfile, "%s", temp->route_name);
    temp = temp->next;
  }
}
void display_routes(struct Routes_t *head) {
  struct Routes_t *temp = head;
  printf("<<< ROUTES >>>\n");
  while (temp != NULL) {
    printf("%s", temp->route_name);
    temp = temp->next;
  }
}

void remove_route(struct Tracker_t *head, char route_name[LEN]) {
  struct Tracker_t *temp = head;
}

void check_duplicate_pokemon(struct Routes_t *head, char pokemon[LEN]) {}

void insert_node(struct Routes_t **head, char data[LEN]) {
  // Create the new node with the given value
  struct Routes_t *new_node = create_node(data);
  if (*head == NULL) {
    *head = new_node;
    return;
  }
  struct Routes_t *temp = *head;
  // Traverse the list until the last node is reached
  while (temp->next != NULL) {
    temp = temp->next;
  }
  // Then set the last node to point to the new node
  temp->next = new_node;
}

void search_route(struct Routes_t *head, char route_name[LEN]) {}
int main() {
  // TODO: Add a way for the program to check if you already have the pokemon,
  // and if so, print it and ask the user to input another pokemon
  struct Routes_t *route = NULL;

  FILE *infile = fopen("kalos_routes.txt", "r");
  FILE *outfile = fopen("kalos.txt", "w");
  if (infile == NULL) {
    printf("Input file is empty.\n");
    fclose(infile);
    return 0;
  }
  char buffer[LEN];
  while (fgets(buffer, sizeof(buffer), infile)) {
    insert_node(&route, buffer);
  }
  // FIX: There is a problem with printing.

  int choice;
  char pokemon[LEN];
  char route_name[LEN];
  while (1) {
    printf("\n<<<< Poke-Tracker >>>>\n");
    printf("1. Display Routes\n");
    printf("2. Remove Route\n");
    printf("3. Check Duplicate Pokemon\n");
    printf("4. Search Route\n");
    printf("5. Exit\n");
    printf("\nEnter Choice: ");
    scanf("%d", &choice);

    switch (choice) {
    case 1:
      clear_terminal();
      display_routes(route);
      break;
    case 2:
      clear_terminal();
      remove_route(route, route_name);
      break;
    case 3:
      clear_terminal();
      check_duplicate_pokemon(route, pokemon);
      break;
    case 4:
      break;
    case 5:
      clear_terminal();
      exit(1);
      break;
    defualt:
      printf("Invalid Input. Try Again.\n");
      break;
    }
  }

  fclose(infile);
  fclose(outfile);
  return 0;
}
