/*----------------------------------------------------------------------------
 * File:  UI_GuiBridge_bridge.c
 *
 * Description:
 * Methods for bridging to an external entity.
 *
 * External Entity:  Graphical User Interface (GuiBridge)
 * 
 * (C) Copyright 1998-2014 Mentor Graphics Corporation.  All rights reserved.
 *--------------------------------------------------------------------------*/

#include "GPSWatch_sys_types.h"
#include "UI_classes.h"
#include "UI_GuiBridge_bridge.h"

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#if defined(_WIN32) || defined(WIN32)
#include <windows.h>
#include <winsock.h>
#include <process.h>

#define WIN32_LEAN_AND_MEAN        /* define win 32 only */

#else

#define SOCKET int

#ifdef __unix__
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <errno.h>
#include <pthread.h>
#endif

#endif

#define SIGNAL_NO_SET_DATA 0
#define SIGNAL_NO_SET_TIME 1


SOCKET sock;                       /* socket details */

void WorkerThread( void *dummy );
#if defined(_WIN32) || defined(WIN32)
void handle_error(void);           /* Error handler routine */
#elif defined(__unix__)
void handle_error(char *msg);           /* Error handler routine */
void handle_error_en(int en, char *msg);
#endif

/*
 * Bridge:  feedSetTargetPressedEvent
 */
void
UI_GuiBridge_feedSetTargetPressedEvent( Escher_xtUMLEvent_t * p_evt )
{
  /* Replace the following instructions with your implementation code.  */
  {
  }
}


/*
 * Bridge:  feedModePressedEvent
 */
void
UI_GuiBridge_feedModePressedEvent( Escher_xtUMLEvent_t * p_evt )
{
  /* Replace the following instructions with your implementation code.  */
  {
  }
}


/*
 * Bridge:  feedLightPressedEvent
 */
void
UI_GuiBridge_feedLightPressedEvent( Escher_xtUMLEvent_t * p_evt )
{
  /* Replace the following instructions with your implementation code.  */
  {
  }
}


/*
 * Bridge:  feedLapResetPressedEvent
 */
void
UI_GuiBridge_feedLapResetPressedEvent( Escher_xtUMLEvent_t * p_evt )
{
  /* Replace the following instructions with your implementation code.  */
  {
  }
}


/*
 * Bridge:  feedStartStopPressedEvent
 */
void
UI_GuiBridge_feedStartStopPressedEvent( Escher_xtUMLEvent_t * p_evt )
{
  /* Replace the following instructions with your implementation code.  */
  {
  }
}


/*
 * Bridge:  setData
 */
void
UI_GuiBridge_setData( i_t p_unit, r_t p_value )
{
  int err;
  char buf[50];
  sprintf(buf, "%d,%f,%d\n", SIGNAL_NO_SET_DATA, p_value, p_unit );
  err = send(sock,buf,strlen(buf),0); 
}


/*
 * Bridge:  setTime
 */
void
UI_GuiBridge_setTime( i_t p_time )
{
  int err;
  char buf[50];
  sprintf(buf, "%d,%d\n", SIGNAL_NO_SET_TIME, p_time);
  err = send(sock,buf,strlen(buf),0);
}


/*
 * Bridge:  connect
 */
void
UI_GuiBridge_connect( void )
{
#if defined(_WIN32) || defined(WIN32)
  WORD wVersionRequested;          /* socket dll version info */
  WSADATA wsaData;                 /* data for socket lib initialisation */
  float socklib_ver;               /* socket dll version */
#elif defined(__unix__)
pthread_t   threadId;
int en;
#endif
  struct sockaddr_in address;      /* socket address stuff */
  char cIP[50];
  
#if defined(_WIN32) || defined(WIN32)
    wVersionRequested = MAKEWORD( 1, 1 );
    if ( WSAStartup( wVersionRequested, &wsaData ) != 0 )
      handle_error();
    socklib_ver = HIBYTE( wsaData.wVersion ) / 10.0;
    socklib_ver += LOBYTE( wsaData.wVersion );
    if ( socklib_ver < 1.1 )
    {
      printf ("\nError: socket library must support 1.1 or greater.\n");
      WSACleanup();
    }
#endif

#if defined(_WIN32) || defined(WIN32)
    if ( (sock = socket(AF_INET, SOCK_STREAM, 0)) == INVALID_SOCKET )
      handle_error();
#elif defined(__unix__)
  if ( (sock = socket(AF_INET, SOCK_STREAM, 0)) < 0 )
    handle_error_en(sock, "Socket creation error ");
#endif

    address.sin_family=AF_INET;       /* internet */
    address.sin_port = htons(2003);   /* port 2003 */

    strcpy(cIP, "127.0.0.1");         /* local host */
    address.sin_addr.s_addr = inet_addr(cIP);
#if defined(_WIN32) || defined(WIN32)
  if ((connect(sock,(struct sockaddr *) &address, sizeof(address))) != 0)
      handle_error();
#elif defined(__unix__)
  if ((en = connect(sock,(struct sockaddr *) &address, sizeof(address))) < 0)
    handle_error_en(en, "Socket connect error ");
#endif
  // start a new thread to handle messages from the GUI
#if defined(_WIN32) || defined(WIN32)
  _beginthread( WorkerThread, 0, NULL );
#elif defined(__unix__)
  if ((en = pthread_create(&threadId, NULL, (void*(*)(void*))WorkerThread, NULL)) < 0)
    handle_error_en(en, "Thread create error ");
#endif
}
 

/*
 * Bridge:  sendModePressed
 */
void
UI_GuiBridge_sendModePressed( void )
{
  /* Replace the following instructions with your implementation code.  */
  {
  /*  SEND UI::modePressed() */
  XTUML_OAL_STMT_TRACE( 1, " SEND UI::modePressed()" );
  UI_UI_modePressed();
  }
}


/*
 * Bridge:  sendLightPressed
 */
void
UI_GuiBridge_sendLightPressed( void )
{
  /* Replace the following instructions with your implementation code.  */
  {
  /*  SEND UI::lightPressed() */
  XTUML_OAL_STMT_TRACE( 1, " SEND UI::lightPressed()" );
  UI_UI_lightPressed();
  }
}


/*
 * Bridge:  sendLapResetPressed
 */
void
UI_GuiBridge_sendLapResetPressed( void )
{
  /* Replace the following instructions with your implementation code.  */
  {
  /*  SEND UI::lapResetPressed() */
  XTUML_OAL_STMT_TRACE( 1, " SEND UI::lapResetPressed()" );
  UI_UI_lapResetPressed();
  }
}


/*
 * Bridge:  sendStartStopPressed
 */
void
UI_GuiBridge_sendStartStopPressed( void )
{
  /* Replace the following instructions with your implementation code.  */
  {
  /*  SEND UI::startStopPressed() */
  XTUML_OAL_STMT_TRACE( 1, " SEND UI::startStopPressed()" );
  UI_UI_startStopPressed();
  }
}


/*
 * Bridge:  sendTargetPressed
 */
void
UI_GuiBridge_sendTargetPressed( void )
{
  /* Replace the following instructions with your implementation code.  */
  {
  /*  SEND UI::setTargetPressed() */
  XTUML_OAL_STMT_TRACE( 1, " SEND UI::setTargetPressed()" );
  UI_UI_setTargetPressed();
  }
}


void WorkerThread( void *dummy )
{
  const int BUF_LEN = 1000;       /* Buffer size for transfers */
  char File_Buf[BUF_LEN];         /* file buffer */
  int res;                        /* error trapping */

  do {
    res = recv(sock, File_Buf, BUF_LEN, 0); // blocking call

	char c = File_Buf[0];
	switch (c) {
	case '0':
      UI_UI_startStopPressed();
      break;
	case '1':
	  UI_UI_setTargetPressed();
      break;
	case '2':
	  UI_UI_lapResetPressed();
      break;
	case '3':
	  UI_UI_lightPressed();
      break;
	case '4':
      UI_UI_modePressed();
      break;
	}
  } while (res > 0);

#if defined(_WIN32) || defined(WIN32)
  WSACleanup();
#endif
  exit(0);
}

#if defined(_WIN32) || defined(WIN32)
void handle_error(void)
{
  switch ( WSAGetLastError() )
  {
  case WSANOTINITIALISED :
    printf("Unable to initialise socket.\n");
    break;
  case WSAEAFNOSUPPORT :
    printf("The specified address family is not supported.\n");
    break;
  case WSAEADDRNOTAVAIL :
    printf("Specified address is not available from the local machine.\n");
    break;
  case WSAECONNREFUSED :
    printf("The attempt to connect was forcefully rejected.\n");
    break;
  case WSAEDESTADDRREQ :
    printf("address destination address is required.\n");
    break;
  case WSAEFAULT :
    printf("The namelen argument is incorrect.\n");
    break;
  case WSAEINVAL :
    printf("The socket is not already bound to an address.\n");
    break;
  case WSAEISCONN :
    printf("The socket is already connected.\n");
    break;
  case WSAEADDRINUSE :
    printf("The specified address is already in use.\n");
    break;
  case WSAEMFILE :
    printf("No more file descriptors are available.\n");
    break;
  case WSAENOBUFS :
    printf("No buffer space available. The socket cannot be created.\n");
    break;
  case WSAEPROTONOSUPPORT :
    printf("The specified protocol is not supported.\n");
    break;
  case WSAEPROTOTYPE :
    printf("The specified protocol is the wrong type for this socket.\n");
    break;
  case WSAENETUNREACH :
    printf("The network can't be reached from this host at this time.\n");
    break;
  case WSAENOTSOCK :
    printf("The descriptor is not a socket.\n");
    break;
  case WSAETIMEDOUT :
    printf("Attempt timed out without establishing a connection.\n");
    break;
  case WSAESOCKTNOSUPPORT :
    printf("Socket type is not supported in this address family.\n");
    break;
  case WSAENETDOWN :
    printf("Network subsystem failure.\n");
    break;
  case WSAHOST_NOT_FOUND :
    printf("Authoritative Answer Host not found.\n");
    break;
  case WSATRY_AGAIN :
    printf("Non-Authoritative Host not found or SERVERFAIL.\n");
    break;
  case WSANO_RECOVERY :
    printf("Non recoverable errors, FORMERR, REFUSED, NOTIMP.\n");
    break;
  case WSANO_DATA :
    printf("Valid name, no data record of requested type.\n");
    break;
  case WSAEINPROGRESS :
    printf("address blocking Windows Sockets operation is in progress.\n");
    break;
  case WSAEINTR :
    printf("The (blocking) call was canceled via WSACancelBlockingCall().\n");
    break;
  default:
    printf("Unknown error.\n");
    break;
  }
  WSACleanup();
}
#elif defined(__unix__)
void handle_error(char *msg)
{
  perror(msg);
  exit(EXIT_FAILURE);
}

void handle_error_en(int en, char *msg)
{
   errno = en;
   handle_error(msg);
}
#endif

