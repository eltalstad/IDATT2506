<%-- Tomas Holt, feb. 2005. Dette er tjenerkoden for øving 6 --%>
<%@page contentType="text/plain;charset=UTF-8" pageEncoding="UTF-8"%>
<%	System.out.println("starter...");
	out.flush();	
	String FERDIG = "Beklager ingen flere sjanser, du må starte på nytt (registrer kortnummer og navn)";
	String navn = request.getParameter("navn");
	if (navn != null){//første gang
		String kortnummer = request.getParameter("kortnummer");
		if (kortnummer == null){
			out.println("Feil, kortnummer er ikke oppgitt!");
			return;
		}
		session.setAttribute("navn",navn);
		session.setAttribute("kortnummer", kortnummer);
		session.setAttribute("teller",new Integer(0));
		int rt = new java.util.Random().nextInt(10) + 1;
		session.setAttribute("riktigTall", new Integer(rt));
		out.println("Oppgi et tall mellom 1 og 10!");
		return;
	}

	//Thread.sleep(7000);
	
	Integer rt = (Integer)session.getAttribute("riktigTall");
	if (rt == null){ 
		out.println("Du har glemt å støtte cookies, eller du har ikke oppgit parameterene navn og kortnummer i første forespørsel!!!");
		return;
	}
	
	int riktigTall = rt.intValue();
	
	Integer ganger = (Integer)session.getAttribute("teller");
	if (ganger == null){
		out.println("Feil, du må registrer navn og kortnummer før du kan tippe (via start nytt spill)");
		return;
	}
	
	int teller = ganger.intValue();
	if (teller > 2) {
		out.println(FERDIG);
	}else{
		String tall = request.getParameter("tall");
		System.out.println("tall " + tall);
		int verdi;
		try{
			 verdi = Integer.parseInt(tall);
		}catch(NumberFormatException e){
			out.println("Tallet er ikke på riktig form, det  være mellom 1 og 10!");
			return;
		}
		teller++;
		session.setAttribute("teller", new Integer(teller));
		String str = "";
		if (verdi == riktigTall){
			String n = (String)session.getAttribute("navn");
			String k = (String)session.getAttribute("kortnummer");
			str = n +", du har vunnet 100 kr som kommer inn på ditt kort " + k;
			//session.invalidate();
		}else if (verdi < riktigTall){
			str = "Tallet " + tall + " er for lite! ";
			if (teller == 3) str += FERDIG;
		}else{
			str = "Tallet " + tall + " er for stort! ";
			if (teller == 3) str += FERDIG;
		}
		out.println(str);
	}
%>
