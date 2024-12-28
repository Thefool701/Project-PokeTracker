#include <malloc.h>
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

struct Routes {
  char route_name[LEN];
  struct Routes *next;
};

struct Routes *create_node(char data[LEN]) {
  struct Routes *new_node = (struct Routes *)malloc(sizeof(struct Routes));
  strcpy(new_node->route_name, data);
  new_node->next = NULL;
  return new_node;
}
void printList(struct Routes *head) {
  struct Routes *temp = head;
  while (temp != NULL) {
    printf("%s -> ", temp->route_name);
    temp = temp->next;
  }
  printf("NULL\n");
}

void printToFile(struct Routes *head, FILE *outfile) {
  struct Routes *temp = head;
  fprintf(outfile,
          "Route          Pokemon Caught          Typing          Status\n");
  while (temp != NULL) {
    fprintf(outfile, "%s", temp->route_name);
    temp = temp->next;
  }
}
void printToScreen(struct Routes *head, FILE *outfile) {
  struct Routes *temp = head;
  printf("Route          Pokemon Caught          Typing          Status\n");
  while (temp != NULL) {
    printf("%s", temp->route_name);
    temp = temp->next;
  }
}

void insert_node(struct Routes **head, char data[LEN]) {
  // Create the new node with the given value
  struct Routes *new_node = create_node(data);
  // Check to see if the head is empty, and if so, make the newNode the new head
  if (*head == NULL) {
    *head = new_node;
    return;
  }
  struct Routes *temp = *head;
  // Traverse the list until the last node is reached
  while (temp->next != NULL) {
    temp = temp->next;
  }
  // Then set the last node to point to the new node
  temp->next = new_node;
}
int main() {
  struct Routes *route = NULL;

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
  // printToFile(route, outfile);

  fclose(infile);
  fclose(outfile);
  return 0;
}
